package com.example.rickandmorty.feature.details.presentation

import com.example.rickandmorty.core.model.Character

data class CharacterDetailsState(
    val character: Character? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed interface CharacterDetailsEvent {
    object OnBackClick : CharacterDetailsEvent
    object OnToggleFavorite : CharacterDetailsEvent
    object OnErrorDismiss : CharacterDetailsEvent
}
