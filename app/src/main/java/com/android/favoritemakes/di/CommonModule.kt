package com.android.favoritemakes.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    fun providesImageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoader.Builder(context).components {
            add(SvgDecoder.Factory())
        }.build()
}