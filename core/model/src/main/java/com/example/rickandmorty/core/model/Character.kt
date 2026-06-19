package com.example.rickandmorty.core.model

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val locationName: String,
    val imageUrl: String,
    val episodeCount: Int,
    val created: String,
    val isFavorite: Boolean = false
)

enum class CharacterStatus {
    ALIVE, DEAD, UNKNOWN;

    fun displayName(): String = when (this) {
        ALIVE -> "Alive"
        DEAD -> "Dead"
        UNKNOWN -> "unknown"
    }

    companion object {
        fun fromString(value: String): CharacterStatus = when (value.lowercase()) {
            "alive" -> ALIVE
            "dead" -> DEAD
            else -> UNKNOWN
        }
    }
}
