package com.example.skillarcade.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skillarcade.data.local.dao.CourseDao
import com.example.skillarcade.data.local.dao.GoalDao
import com.example.skillarcade.data.local.dao.LessonDao
import com.example.skillarcade.data.local.dao.TrophyDao
import com.example.skillarcade.data.local.dao.UserProgressDao
import com.example.skillarcade.data.local.entities.CourseEntity
import com.example.skillarcade.data.local.entities.GoalEntity
import com.example.skillarcade.data.local.entities.LessonEntity
import com.example.skillarcade.data.local.entities.TrophyEntity
import com.example.skillarcade.data.local.entities.UserProgressEntity

@Database(
    entities = [
        CourseEntity::class,
        LessonEntity::class,
        GoalEntity::class,
        TrophyEntity::class,
        UserProgressEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class SkillArcadeDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao
    abstract fun goalDao(): GoalDao
    abstract fun trophyDao(): TrophyDao
    abstract fun userProgressDao(): UserProgressDao
}
