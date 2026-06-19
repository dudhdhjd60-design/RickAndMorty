package com.example.rickandmorty.core.network.api

import com.example.rickandmorty.core.network.dto.CharacterDto
import com.example.rickandmorty.core.network.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("status") status: String? = null
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto
}
