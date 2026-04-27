package com.example.skillarcade.domain.repository

import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface SkillArcadeRepository {
    fun getCourses(): Flow<List<Course>>
    fun getCourse(courseId: String): Flow<Course?>
    fun getLessons(courseId: String): Flow<List<Lesson>>
    fun getLesson(lessonId: String): Flow<Lesson?>
    fun getGoals(): Flow<List<Goal>>
    fun getTrophies(): Flow<List<Trophy>>
    fun getUserProgress(): Flow<UserProgress>
    suspend fun completeLesson(lessonId: String)
    suspend fun updateGoalProgress(goalId: String, value: Int)
    suspend fun unlockTrophy(trophyId: String)
    suspend fun addXp(amount: Int)
}
