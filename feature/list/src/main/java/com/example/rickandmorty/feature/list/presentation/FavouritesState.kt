package com.example.rickandmorty.feature.list.presentation

import com.example.rickandmorty.core.model.Character

data class FavouritesState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = true
)

sealed interface FavouritesEvent {
    data class OnCharacterClick(val characterId: Int) : FavouritesEvent
}
