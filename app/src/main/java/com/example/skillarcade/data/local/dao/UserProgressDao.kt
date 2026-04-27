package com.example.skillarcade.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skillarcade.data.local.entities.UserProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = :id")
    fun getById(id: String): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgressEntity)

    @Query("UPDATE user_progress SET totalXp = totalXp + :amount, level = (totalXp + :amount) / 100 + 1, lastActiveDate = :timestamp WHERE id = :id")
    suspend fun addXp(id: String, amount: Int, timestamp: Long)

    @Query("UPDATE user_progress SET lessonsCompleted = lessonsCompleted + 1 WHERE id = :id")
    suspend fun incrementLessonsCompleted(id: String)

    @Query("UPDATE user_progress SET coursesCompleted = coursesCompleted + 1 WHERE id = :id")
    suspend fun incrementCoursesCompleted(id: String)
}
