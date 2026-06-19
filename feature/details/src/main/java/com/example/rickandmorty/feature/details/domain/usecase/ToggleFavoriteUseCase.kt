package com.example.rickandmorty.feature.details.domain.usecase

import com.example.rickandmorty.feature.details.domain.CharacterDetailsRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CharacterDetailsRepository
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) =
        repository.toggleFavorite(id, isFavorite)
}
