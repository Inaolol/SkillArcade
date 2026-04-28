package com.example.skillarcade.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skillarcade.data.local.entities.TrophyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrophyDao {
    @Query("SELECT * FROM trophies")
    fun getAll(): Flow<List<TrophyEntity>>

    @Query("SELECT COUNT(*) FROM trophies")
    suspend fun countTrophies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trophies: List<TrophyEntity>)

    @Query("UPDATE trophies SET isUnlocked = 1, unlockedAt = :timestamp WHERE id = :trophyId")
    suspend fun unlock(trophyId: String, timestamp: Long)
}
