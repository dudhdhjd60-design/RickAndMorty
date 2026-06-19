package com.example.rickandmorty.feature.list.domain.usecase

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.feature.list.domain.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: CharacterListRepository
) {
    operator fun invoke(): Flow<List<Character>> = repository.getFavourites()
}
