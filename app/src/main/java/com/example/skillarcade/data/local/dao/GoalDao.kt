package com.example.skillarcade.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skillarcade.data.local.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals")
    fun getAll(): Flow<List<GoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goals: List<GoalEntity>)

    @Query("UPDATE goals SET currentValue = :value, isCompleted = CASE WHEN isCompleted = 1 THEN 1 WHEN :value >= targetValue THEN 1 ELSE 0 END WHERE id = :goalId")
    suspend fun updateProgress(goalId: String, value: Int)

    @Query("UPDATE goals SET isCompleted = 1 WHERE id = :goalId")
    suspend fun markCompleted(goalId: String)
}
