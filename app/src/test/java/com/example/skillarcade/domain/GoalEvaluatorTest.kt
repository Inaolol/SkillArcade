package com.example.skillarcade.domain

import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.GoalType
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.usecase.GoalEvaluator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GoalEvaluatorTest {

    private lateinit var evaluator: GoalEvaluator

    @Before
    fun setUp() {
        evaluator = GoalEvaluator()
    }

    // ── isGoalComplete ────────────────────────────────────────────────────────

    @Test
    fun `isGoalComplete returns false when currentValue is below targetValue`() {
        val goal = makeGoal(currentValue = 3, targetValue = 10)
        assertFalse(evaluator.isGoalComplete(goal))
    }

    @Test
    fun `isGoalComplete returns true when currentValue equals targetValue`() {
        val goal = makeGoal(currentValue = 10, targetValue = 10)
        assertTrue(evaluator.isGoalComplete(goal))
    }

    @Test
    fun `isGoalComplete returns true when currentValue exceeds targetValue`() {
        val goal = makeGoal(currentValue = 15, targetValue = 10)
        assertTrue(evaluator.isGoalComplete(goal))
    }

    // ── goalProgressFraction ──────────────────────────────────────────────────

    @Test
    fun `goalProgressFraction returns 0f when currentValue is 0`() {
        val goal = makeGoal(currentValue = 0, targetValue = 10)
        assertEquals(0f, evaluator.goalProgressFraction(goal), 0f)
    }

    @Test
    fun `goalProgressFraction returns 0_5f for halfway progress`() {
        val goal = makeGoal(currentValue = 5, targetValue = 10)
        assertEquals(0.5f, evaluator.goalProgressFraction(goal), 0.0001f)
    }

    @Test
    fun `goalProgressFraction returns 1f when goal is exactly met`() {
        val goal = makeGoal(currentValue = 10, targetValue = 10)
        assertEquals(1f, evaluator.goalProgressFraction(goal), 0f)
    }

    @Test
    fun `goalProgressFraction clamps to 1f when currentValue exceeds targetValue`() {
        val goal = makeGoal(currentValue = 15, targetValue = 10)
        assertEquals(1f, evaluator.goalProgressFraction(goal), 0f)
    }

    @Test
    fun `goalProgressFraction returns 0f when targetValue is 0`() {
        val goal = makeGoal(currentValue = 0, targetValue = 0)
        assertEquals(0f, evaluator.goalProgressFraction(goal), 0f)
    }

    // ── evaluateGoals ─────────────────────────────────────────────────────────

    @Test
    fun `evaluateGoals does not un-complete an already-completed goal (ratchet)`() {
        val completedGoal = makeGoal(
            currentValue = 10,
            targetValue = 10,
            isCompleted = true,
            goalType = GoalType.LESSONS_COMPLETED
        )
        // Progress with 0 lessons — would normally regress a non-completed goal
        val progress = UserProgress(lessonsCompleted = 0)
        val result = evaluator.evaluateGoals(listOf(completedGoal), progress)
        assertTrue(result.first().isCompleted)
        assertEquals(10, result.first().currentValue)
    }

    @Test
    fun `evaluateGoals maps LESSONS_COMPLETED goal to progress_lessonsCompleted`() {
        val goal = makeGoal(currentValue = 0, targetValue = 5, goalType = GoalType.LESSONS_COMPLETED)
        val progress = UserProgress(lessonsCompleted = 3)
        val result = evaluator.evaluateGoals(listOf(goal), progress)
        assertEquals(3, result.first().currentValue)
        assertFalse(result.first().isCompleted)
    }

    @Test
    fun `evaluateGoals maps XP_EARNED goal to progress_totalXp`() {
        val goal = makeGoal(currentValue = 0, targetValue = 100, goalType = GoalType.XP_EARNED)
        val progress = UserProgress(totalXp = 120)
        val result = evaluator.evaluateGoals(listOf(goal), progress)
        assertEquals(120, result.first().currentValue)
        assertTrue(result.first().isCompleted)
    }

    @Test
    fun `evaluateGoals marks incomplete goal as completed when threshold is reached`() {
        val goal = makeGoal(currentValue = 4, targetValue = 5, goalType = GoalType.LESSONS_COMPLETED)
        val progress = UserProgress(lessonsCompleted = 5)
        val result = evaluator.evaluateGoals(listOf(goal), progress)
        assertEquals(5, result.first().currentValue)
        assertTrue(result.first().isCompleted)
    }

    @Test
    fun `evaluateGoals maps COURSES_COMPLETED goal to progress_coursesCompleted`() {
        val goal = makeGoal(currentValue = 0, targetValue = 3, goalType = GoalType.COURSES_COMPLETED)
        val progress = UserProgress(coursesCompleted = 2)
        val result = evaluator.evaluateGoals(listOf(goal), progress)
        assertEquals(2, result.first().currentValue)
        assertFalse(result.first().isCompleted)
    }

    @Test
    fun `evaluateGoals maps STREAK_DAYS goal to progress_streak`() {
        val goal = makeGoal(currentValue = 0, targetValue = 7, goalType = GoalType.STREAK_DAYS)
        val progress = UserProgress(streak = 7)
        val result = evaluator.evaluateGoals(listOf(goal), progress)
        assertEquals(7, result.first().currentValue)
        assertTrue(result.first().isCompleted)
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private fun makeGoal(
        currentValue: Int,
        targetValue: Int,
        isCompleted: Boolean = false,
        goalType: GoalType = GoalType.LESSONS_COMPLETED
    ) = Goal(
        id = "g1",
        title = "Test Goal",
        description = "",
        targetValue = targetValue,
        currentValue = currentValue,
        xpReward = 50,
        isCompleted = isCompleted,
        goalType = goalType
    )
}
