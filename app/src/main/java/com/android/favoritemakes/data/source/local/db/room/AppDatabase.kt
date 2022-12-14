package com.android.favoritemakes.data.source.local.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.favoritemakes.data.source.local.db.room.model.MakeModel

@Database(entities = [MakeModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun makesDao(): MakesDao
}