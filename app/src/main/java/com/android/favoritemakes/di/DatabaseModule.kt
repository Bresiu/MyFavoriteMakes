package com.android.favoritemakes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.favoritemakes.data.source.local.db.MakeRepository
import com.android.favoritemakes.data.source.local.db.room.AppDatabase
import com.android.favoritemakes.data.source.local.db.room.MakeDao
import com.android.favoritemakes.data.source.local.db.room.MakeRoomRepository
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
    fun providesMakeDao(appDatabase: AppDatabase): MakeDao = appDatabase.makeDao()

    @Provides
    internal fun provideMakeRepository(makeDao: MakeDao): MakeRepository =
        MakeRoomRepository(makeDao)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}