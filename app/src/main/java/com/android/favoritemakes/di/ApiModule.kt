package com.android.favoritemakes.di

import com.android.favoritemakes.data.source.remote.SyncService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Named("baseURL")
    fun providesAPIUrl() = "https://TODO-provide_real_url_to_fetch_makes_list"

    @Provides
    fun providesSyncService(retrofit: Retrofit): SyncService =
        retrofit.create(SyncService::class.java)

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun providesRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        @Named("baseURL") baseUrl: String,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
}