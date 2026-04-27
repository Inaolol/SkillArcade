package com.example.skillarcade.domain.model

data class UserProgress(
    val id: String = "user",
    val totalXp: Int = 0,
    val level: Int = 1,
    val streak: Int = 0,
    val lessonsCompleted: Int = 0,
    val coursesCompleted: Int = 0,
    val lastActiveDate: Long = 0L
)
