package com.example.rickandmorty.feature.list.domain.usecase

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus
import com.example.rickandmorty.feature.list.domain.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterListRepository
) {
    operator fun invoke(status: CharacterStatus? = null): Flow<List<Character>> {
        return if (status == null) {
            repository.getCharacters()
        } else {
            repository.getCharactersByStatus(status)
        }
    }
}
