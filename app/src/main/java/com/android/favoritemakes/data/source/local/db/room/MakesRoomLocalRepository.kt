package com.android.favoritemakes.data.source.local.db.room

import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import kotlinx.coroutines.flow.Flow

class MakesRoomLocalRepository(
    private val makesDao: MakesDao,
) : MakesLocalRepository {
    override suspend fun insertAll(list: List<MakeModel>) {
        makesDao.insertAll(list)
    }

    override fun getAll(): Flow<List<MakeModel>> = makesDao.getAll()

    override fun getFavoritesCount(): Flow<Int> = makesDao.getFavoritesCount()

    override suspend fun updateMakes(makes: List<MakeModel>) {
        makesDao.updateMakes(makes)
    }

    override suspend fun favoriteMake(makeId: Long) {
        makesDao.favoriteMake(makeId)
    }

    override suspend fun unfavoriteMake(makeId: Long) {
        makesDao.unfavoriteMake(makeId)
    }
}