package com.example.rickandmorty.feature.list.domain.usecase

import com.example.rickandmorty.feature.list.domain.CharacterListRepository
import javax.inject.Inject

class RefreshCharactersUseCase @Inject constructor(
    private val repository: CharacterListRepository
) {
    suspend operator fun invoke() = repository.refreshCharacters()
}
