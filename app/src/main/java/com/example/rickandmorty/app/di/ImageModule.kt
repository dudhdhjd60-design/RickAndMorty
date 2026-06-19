package com.example.rickandmorty.app.di

import android.app.Application
import android.graphics.Bitmap
import coil.ImageLoader
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    @Singleton
    @Named("coil")
    fun provideCoilOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        app: Application,
        @Named("coil") okHttpClient: OkHttpClient
    ): ImageLoader =
        ImageLoader.Builder(app)
            .okHttpClient(okHttpClient)
            .memoryCache {
                MemoryCache.Builder(app)
                    .maxSizePercent(0.40)
                    .build()
            }
            .diskCache(null)
            .crossfade(300)
            .respectCacheHeaders(false)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
}
