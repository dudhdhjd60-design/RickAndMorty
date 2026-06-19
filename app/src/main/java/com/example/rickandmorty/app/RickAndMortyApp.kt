package com.example.rickandmorty.app

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RickAndMortyApp : Application() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        Log.d("RickMortyApp", "onCreate called")
        Coil.setImageLoader(imageLoader)
        Log.d("RickMortyApp", "Coil ImageLoader set: $imageLoader")
    }
}
