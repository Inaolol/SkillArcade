package com.example.skillarcade.domain.model

data class UserProgress(
    val id: String = SINGLE_USER_ID,
    val totalXp: Int = 0,
    val level: Int = 1,
    val streak: Int = 0,
    val lessonsCompleted: Int = 0,
    val coursesCompleted: Int = 0,
    val lastActiveDate: Long = 0L
) {
    companion object {
        const val SINGLE_USER_ID = "user"
    }
}
