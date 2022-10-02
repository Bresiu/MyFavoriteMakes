package com.android.favoritemakes.data.source.local.db.room

import com.android.favoritemakes.data.source.local.db.MakeRepository
import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import kotlinx.coroutines.flow.Flow

class MakeRoomRepository(
    private val makeDao: MakeDao,
) : MakeRepository {
    override suspend fun insertAll(list: List<MakeModel>) {
        makeDao.insertAll(list)
    }

    override fun getAll(): Flow<List<MakeModel>> = makeDao.getAll()

    override fun getFavoritesCount(): Flow<Int> = makeDao.getFavoritesCount()

    override suspend fun updateMakes(makes: List<MakeModel>) {
        makeDao.updateMakes(makes)
    }

    override suspend fun favoriteMake(makeId: Long) {
        makeDao.favoriteMake(makeId)
    }

    override suspend fun unfavoriteMake(makeId: Long) {
        makeDao.unfavoriteMake(makeId)
    }
}