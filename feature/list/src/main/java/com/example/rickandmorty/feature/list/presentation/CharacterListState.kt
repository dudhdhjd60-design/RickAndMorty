package com.example.rickandmorty.feature.list.presentation

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val selectedStatus: CharacterStatus? = null
)

sealed interface CharacterListEvent {
    data class OnCharacterClick(val characterId: Int) : CharacterListEvent
    data class OnStatusFilterChange(val status: CharacterStatus?) : CharacterListEvent
    object OnRefresh : CharacterListEvent
    object OnErrorDismiss : CharacterListEvent
}
