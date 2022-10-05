package com.android.favoritemakes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import com.android.favoritemakes.data.source.local.db.room.AppDatabase
import com.android.favoritemakes.data.source.local.db.room.MakesDao
import com.android.favoritemakes.data.source.local.db.room.MakesRoomLocalRepository
import com.android.favoritemakes.utilities.DATABASE_NAME
import com.android.favoritemakes.utilities.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun providesMakesDao(appDatabase: AppDatabase): MakesDao = appDatabase.makesDao()

    @Provides
    internal fun provideMakesLocalRepository(makesDao: MakesDao): MakesLocalRepository =
        MakesRoomLocalRepository(makesDao)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}