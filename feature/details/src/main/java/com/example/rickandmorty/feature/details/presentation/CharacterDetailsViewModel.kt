package com.example.rickandmorty.feature.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.feature.details.domain.usecase.GetCharacterDetailsUseCase
import com.example.rickandmorty.feature.details.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> = _state.asStateFlow()

    init {
        observeCharacter()
    }

    fun onEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.OnToggleFavorite -> toggleFavorite()
            is CharacterDetailsEvent.OnErrorDismiss -> {
                _state.update { it.copy(error = null) }
            }
            is CharacterDetailsEvent.OnBackClick -> { /* Handled by NavGraph */ }
        }
    }

    private fun observeCharacter() {
        getCharacterDetailsUseCase(characterId)
            .onEach { character ->
                _state.update { it.copy(character = character, isLoading = false) }
            }
            .catch { e ->
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun toggleFavorite() {
        val character = _state.value.character ?: return
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(character.id, !character.isFavorite)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}
