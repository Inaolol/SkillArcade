package com.example.skillarcade.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: String,
    val totalXp: Int,
    val level: Int,
    val streak: Int,
    val lessonsCompleted: Int,
    val coursesCompleted: Int,
    val lastActiveDate: Long
)
