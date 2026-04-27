package com.example.skillarcade.domain.model

data class Goal(
    val id: String,
    val title: String,
    val description: String,
    val targetValue: Int,
    val currentValue: Int,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val goalType: GoalType
)

enum class GoalType { LESSONS_COMPLETED, COURSES_COMPLETED, XP_EARNED, STREAK_DAYS }
