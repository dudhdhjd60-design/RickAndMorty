package com.example.rickandmorty.feature.list.domain

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    fun getCharacters(): Flow<List<Character>>
    fun getCharactersByStatus(status: CharacterStatus): Flow<List<Character>>
    fun getFavourites(): Flow<List<Character>>
    suspend fun refreshCharacters()
}
