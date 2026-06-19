package com.example.rickandmorty.core.database

import com.example.rickandmorty.core.model.Character
import com.example.rickandmorty.core.model.CharacterStatus

object CharacterMapper {
    fun CharacterEntity.toCharacter(): Character = Character(
        id = id.toInt(),
        name = name,
        status = CharacterStatus.fromString(status),
        species = species,
        type = type,
        gender = gender,
        originName = originName,
        locationName = locationName,
        imageUrl = imageUrl,
        episodeCount = episodeCount.toInt(),
        created = created,
        isFavorite = isFavorite != 0L
    )
}
