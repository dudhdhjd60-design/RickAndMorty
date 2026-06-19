package com.example.rickandmorty.feature.details.domain.usecase

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.feature.details.domain.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val repository: CharacterDetailsRepository
) {
    operator fun invoke(id: Int): Flow<Character?> = repository.getCharacterById(id)
}
