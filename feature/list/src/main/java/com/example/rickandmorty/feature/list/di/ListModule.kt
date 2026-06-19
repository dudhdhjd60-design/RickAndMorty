package com.example.rickandmorty.feature.list.di

import com.example.rickandmorty.feature.list.data.repository.CharacterListRepositoryImpl
import com.example.rickandmorty.feature.list.domain.CharacterListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ListModule {

    @Binds
    @Singleton
    abstract fun bindCharacterListRepository(
        impl: CharacterListRepositoryImpl
    ): CharacterListRepository
}
