package com.example.skillarcade.domain.usecase

import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Lesson

class ProgressCalculator {
    /** Returns a value in [0,1] representing the fraction of lessons completed in [course]. */
    fun courseCompletionFraction(course: Course): Float =
        if (course.totalLessons == 0) 0f
        else course.completedLessons.toFloat() / course.totalLessons.toFloat()

    fun levelFromXp(totalXp: Int): Int = (totalXp / 100) + 1

    /** Returns XP needed to reach the next level, or 0 if [totalXp] is exactly on a level boundary. */
    fun xpToNextLevel(totalXp: Int): Int {
        val remainder = totalXp % 100
        return if (remainder == 0) 0 else 100 - remainder
    }

    fun totalXpFromLessons(lessons: List<Lesson>): Int =
        lessons.filter { it.isCompleted }.sumOf { it.xpReward }
}
