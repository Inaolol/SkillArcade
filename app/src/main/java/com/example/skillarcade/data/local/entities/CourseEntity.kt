package com.example.skillarcade.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val xpReward: Int,
    val difficulty: String,   // store enum name as String
    val thumbnailUrl: String,
    val durationHours: Int = 0,
    val tag: String? = null
)
