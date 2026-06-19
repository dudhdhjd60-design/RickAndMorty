package com.example.rickandmorty.feature.details.domain

import com.example.rickandmorty.core.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsRepository {
    fun getCharacterById(id: Int): Flow<Character?>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
}
