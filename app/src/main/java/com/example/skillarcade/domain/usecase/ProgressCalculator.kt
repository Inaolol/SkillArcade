package com.example.skillarcade.domain.usecase

import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Lesson

class ProgressCalculator {
    fun courseCompletionPercent(course: Course): Float =
        if (course.totalLessons == 0) 0f
        else course.completedLessons.toFloat() / course.totalLessons.toFloat()

    fun levelFromXp(totalXp: Int): Int = (totalXp / 100) + 1

    fun xpToNextLevel(totalXp: Int): Int = 100 - (totalXp % 100)

    fun totalXpFromLessons(lessons: List<Lesson>): Int =
        lessons.filter { it.isCompleted }.sumOf { it.xpReward }
}
