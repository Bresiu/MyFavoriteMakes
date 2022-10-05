package com.android.favoritemakes.data.source.local.db.room

import androidx.room.*
import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MakesDao {
    @Query("SELECT * FROM makes")
    fun getAll(): Flow<List<MakeModel>>

    @Query("SELECT COUNT(*) FROM makes WHERE is_favorite = 1")
    fun getFavoritesCount(): Flow<Int>

    @Transaction
    suspend fun updateMakes(makes: List<MakeModel>) {
        deleteAllMakes()
        insertAll(makes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(makes: List<MakeModel>)

    @Query("DELETE FROM Makes")
    suspend fun deleteAllMakes()

    @Query("UPDATE makes SET is_favorite = 1 WHERE id = :makeId")
    fun favoriteMake(makeId: Long)

    @Query("UPDATE makes SET is_favorite = 0 WHERE id = :makeId")
    fun unfavoriteMake(makeId: Long)
}