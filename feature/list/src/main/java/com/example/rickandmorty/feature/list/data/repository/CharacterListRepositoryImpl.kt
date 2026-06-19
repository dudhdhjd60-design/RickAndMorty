package com.example.rickandmorty.feature.list.data.repository

import com.example.rickandmorty.core.database.CharacterMapper.toCharacter
import com.example.rickandmorty.core.database.CharacterQueries
import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus
import com.example.rickandmorty.core.network.api.RickAndMortyApi
import com.example.rickandmorty.core.network.dto.toCharacter
import com.example.rickandmorty.feature.list.domain.CharacterListRepository
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterListRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val queries: CharacterQueries
) : CharacterListRepository {

    override fun getCharacters(): Flow<List<Character>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toCharacter() } }
    }

    override fun getCharactersByStatus(status: CharacterStatus): Flow<List<Character>> {
        return queries.selectByStatus(status.displayName())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toCharacter() } }
    }

    override fun getFavourites(): Flow<List<Character>> {
        return queries.selectFavourites()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toCharacter() } }
    }

    override suspend fun refreshCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val favoriteIds = queries.selectAll().executeAsList()
                    .filter { it.isFavorite != 0L }
                    .map { it.id }
                    .toSet()

                // Fetch first page only for fast initial load
                val allCharacters = mutableListOf<com.example.rickandmorty.core.network.dto.CharacterDto>()
                try {
                    val response = api.getCharacters(page = 1)
                    allCharacters.addAll(response.results)
                } catch (e: Exception) {
                    // Network unavailable — use cached data
                }

                allCharacters.forEach { dto ->
                    val isFavorite = dto.id.toLong() in favoriteIds
                    val character = dto.toCharacter(isFavorite)
                    queries.insert(
                        id = character.id.toLong(),
                        name = character.name,
                        status = character.status.displayName(),
                        species = character.species,
                        type = character.type,
                        gender = character.gender,
                        originName = character.originName,
                        locationName = character.locationName,
                        imageUrl = character.imageUrl,
                        episodeCount = character.episodeCount.toLong(),
                        created = character.created,
                        isFavorite = if (isFavorite) 1L else 0L
                    )
                }
            } catch (e: Exception) {
                // Network unavailable — use cached data
            }
        }
    }
}
