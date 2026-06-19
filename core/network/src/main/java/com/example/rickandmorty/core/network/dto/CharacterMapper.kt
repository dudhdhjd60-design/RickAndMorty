package com.example.rickandmorty.core.network.dto

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus

fun CharacterDto.toCharacter(isFavorite: Boolean = false): Character = Character(
    id = id,
    name = name,
    status = CharacterStatus.fromString(status),
    species = species,
    type = type,
    gender = gender,
    originName = origin.name,
    locationName = location.name,
    imageUrl = image,
    episodeCount = episode.size,
    created = created,
    isFavorite = isFavorite
)
