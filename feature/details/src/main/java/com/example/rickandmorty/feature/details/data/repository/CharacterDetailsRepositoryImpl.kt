package com.example.rickandmorty.feature.details.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.rickandmorty.core.database.CharacterMapper.toCharacter
import com.example.rickandmorty.core.database.CharacterQueries
import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.feature.details.domain.CharacterDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val queries: CharacterQueries
) : CharacterDetailsRepository {

    override fun getCharacterById(id: Int): Flow<Character?> {
        return queries.selectById(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toCharacter() }
    }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            queries.updateFavorite(
                isFavorite = if (isFavorite) 1L else 0L,
                id = id.toLong()
            )
        }
    }
}
