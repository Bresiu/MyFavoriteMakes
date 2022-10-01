package com.android.favoritemakes.data.source.local.db.room

import androidx.room.*
import com.android.favoritemakes.data.source.local.db.room.model.Make
import kotlinx.coroutines.flow.Flow

@Dao
interface MakeDao {
    @Query("SELECT * FROM makes")
    fun getAll(): Flow<List<Make>>

    @Query("SELECT COUNT(*) FROM makes WHERE is_favorite = 1")
    fun getFavoritesCount(): Flow<Int>

    @Transaction
    fun updateMakes(makes: List<Make>) {
        deleteAllMakes()
        insertAll(makes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(makes: List<Make>)

    @Query("DELETE FROM Makes")
    fun deleteAllMakes()
}