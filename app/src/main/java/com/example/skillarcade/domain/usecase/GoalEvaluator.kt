package com.example.skillarcade.domain.usecase

import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.GoalType
import com.example.skillarcade.domain.model.UserProgress

class GoalEvaluator {
    fun isGoalComplete(goal: Goal): Boolean = goal.currentValue >= goal.targetValue

    /** Returns a value in [0,1] representing how far along [goal] the user is. */
    fun goalProgressFraction(goal: Goal): Float =
        if (goal.targetValue == 0) 0f
        else (goal.currentValue.toFloat() / goal.targetValue.toFloat()).coerceIn(0f, 1f)

    fun evaluateGoals(goals: List<Goal>, progress: UserProgress): List<Goal> =
        goals.map { goal ->
            if (goal.isCompleted) return@map goal  // one-way ratchet: never regress
            val current = when (goal.goalType) {
                GoalType.LESSONS_COMPLETED -> progress.lessonsCompleted
                GoalType.COURSES_COMPLETED -> progress.coursesCompleted
                GoalType.XP_EARNED -> progress.totalXp
                GoalType.STREAK_DAYS -> progress.streak
            }
            goal.copy(
                currentValue = current,
                isCompleted = current >= goal.targetValue
            )
        }
}
