package com.example.skillarcade.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val targetValue: Int,
    val currentValue: Int,
    val xpReward: Int,
    val isCompleted: Boolean,
    val goalType: String    // store enum name as String
)
