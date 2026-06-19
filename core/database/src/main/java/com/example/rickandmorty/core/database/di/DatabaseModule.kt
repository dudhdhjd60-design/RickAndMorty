package com.example.rickandmorty.core.database.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.rickandmorty.core.database.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return RickAndMortyDatabase(
            AndroidSqliteDriver(
                schema = RickAndMortyDatabase.Schema,
                context = context,
                name = "rick_and_morty.db"
            )
        )
    }

    @Provides
    @Singleton
    fun provideCharacterQueries(database: RickAndMortyDatabase) = database.characterQueries
}
