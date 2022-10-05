package com.android.favoritemakes.di

import android.content.Context
import com.android.favoritemakes.data.source.remote.MakesApiRemoteRepository
import com.android.favoritemakes.data.source.remote.retrofit.MakesApi
import com.android.favoritemakes.data.source.remote.MakesRemoteRepository
import com.android.favoritemakes.data.source.remote.assets.AssetsMakesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Named("baseURL")
    fun providesAPIUrl() = "https://TODO-provide_real_url_to_fetch_makes_list"

    @Remote
    @Provides
    fun providesRemoteMakesApi(retrofit: Retrofit): MakesApi =
        retrofit.create(MakesApi::class.java)

    @Assets
    @Provides
    fun providesAssetsMakesApi(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): MakesApi = AssetsMakesApi(context, moshi)

    @Provides
    fun providesAssetsMakesRepository(
        @Assets makesApi: MakesApi,
    ): MakesRemoteRepository = MakesApiRemoteRepository(makesApi)

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

    @Qualifier
    annotation class Assets

    @Qualifier
    annotation class Remote
}