package com.example.skillarcade.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val title: String,
    val youtubeUrl: String,
    val durationMinutes: Int,
    val orderIndex: Int,
    val isCompleted: Boolean,
    val xpReward: Int
)
