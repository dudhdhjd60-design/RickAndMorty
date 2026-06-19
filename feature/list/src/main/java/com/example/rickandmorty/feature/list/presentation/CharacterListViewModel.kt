package com.example.rickandmorty.feature.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.core.model.CharacterStatus
import com.example.rickandmorty.feature.list.domain.usecase.GetCharactersUseCase
import com.example.rickandmorty.feature.list.domain.usecase.RefreshCharactersUseCase
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
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        observeCharacters(null)
        initialLoad()
    }

    fun onEvent(event: CharacterListEvent) {
        when (event) {
            is CharacterListEvent.OnStatusFilterChange -> {
                _state.update { it.copy(selectedStatus = event.status) }
                observeCharacters(event.status)
            }
            is CharacterListEvent.OnRefresh -> refresh()
            is CharacterListEvent.OnErrorDismiss -> {
                _state.update { it.copy(error = null) }
            }
            is CharacterListEvent.OnCharacterClick -> { /* Handled by NavGraph */ }
        }
    }

    private fun observeCharacters(status: CharacterStatus?) {
        getCharactersUseCase(status)
            .onEach { characters ->
                _state.update { it.copy(characters = characters, isLoading = false) }
            }
            .catch { e ->
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun initialLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                refreshCharactersUseCase()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            try {
                refreshCharactersUseCase()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isRefreshing = false) }
            }
        }
    }
}
