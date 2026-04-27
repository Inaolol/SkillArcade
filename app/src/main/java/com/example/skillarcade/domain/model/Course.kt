package com.example.skillarcade.domain.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val xpReward: Int,
    val difficulty: Difficulty,
    val thumbnailUrl: String = ""
)

enum class Difficulty { EASY, MEDIUM, HARD }
