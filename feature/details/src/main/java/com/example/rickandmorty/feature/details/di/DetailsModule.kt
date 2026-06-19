package com.example.rickandmorty.feature.details.di

import com.example.rickandmorty.feature.details.data.repository.CharacterDetailsRepositoryImpl
import com.example.rickandmorty.feature.details.domain.CharacterDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsModule {

    @Binds
    @Singleton
    abstract fun bindCharacterDetailsRepository(
        impl: CharacterDetailsRepositoryImpl
    ): CharacterDetailsRepository
}
