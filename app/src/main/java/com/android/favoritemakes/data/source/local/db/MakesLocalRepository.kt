package com.android.favoritemakes.data.source.local.db

import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import kotlinx.coroutines.flow.Flow

interface MakesLocalRepository {
    suspend fun insertAll(list: List<MakeModel>)
    fun getAll(): Flow<List<MakeModel>>
    fun getFavoritesCount(): Flow<Int>
    suspend fun updateMakes(makes: List<MakeModel>)
    suspend fun favoriteMake(makeId: Long)
    suspend fun unfavoriteMake(makeId: Long)
}