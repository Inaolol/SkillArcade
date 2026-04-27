package com.example.skillarcade.domain

import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.usecase.ProgressCalculator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProgressCalculatorTest {

    private lateinit var calculator: ProgressCalculator

    @Before
    fun setUp() {
        calculator = ProgressCalculator()
    }

    // ── courseCompletionFraction ──────────────────────────────────────────────

    @Test
    fun `courseCompletionFraction returns 0f when totalLessons is 0`() {
        val course = makeCourse(totalLessons = 0, completedLessons = 0)
        assertEquals(0f, calculator.courseCompletionFraction(course), 0f)
    }

    @Test
    fun `courseCompletionFraction returns partial fraction when some lessons are completed`() {
        val course = makeCourse(totalLessons = 4, completedLessons = 1)
        assertEquals(0.25f, calculator.courseCompletionFraction(course), 0.0001f)
    }

    @Test
    fun `courseCompletionFraction returns 1f when all lessons are completed`() {
        val course = makeCourse(totalLessons = 5, completedLessons = 5)
        assertEquals(1f, calculator.courseCompletionFraction(course), 0f)
    }

    // ── levelFromXp ───────────────────────────────────────────────────────────

    @Test
    fun `levelFromXp returns 1 for 0 XP`() {
        assertEquals(1, calculator.levelFromXp(0))
    }

    @Test
    fun `levelFromXp returns 1 for 99 XP`() {
        assertEquals(1, calculator.levelFromXp(99))
    }

    @Test
    fun `levelFromXp returns 2 for 100 XP`() {
        assertEquals(2, calculator.levelFromXp(100))
    }

    @Test
    fun `levelFromXp returns 3 for 200 XP`() {
        assertEquals(3, calculator.levelFromXp(200))
    }

    // ── xpToNextLevel ─────────────────────────────────────────────────────────

    @Test
    fun `xpToNextLevel returns 0 for 0 XP`() {
        // 0 % 100 == 0, so the fix returns 0
        assertEquals(0, calculator.xpToNextLevel(0))
    }

    @Test
    fun `xpToNextLevel returns 50 for 50 XP`() {
        assertEquals(50, calculator.xpToNextLevel(50))
    }

    @Test
    fun `xpToNextLevel returns 1 for 99 XP`() {
        assertEquals(1, calculator.xpToNextLevel(99))
    }

    @Test
    fun `xpToNextLevel returns 0 for 100 XP (exactly on a level boundary)`() {
        assertEquals(0, calculator.xpToNextLevel(100))
    }

    // ── totalXpFromLessons ────────────────────────────────────────────────────

    @Test
    fun `totalXpFromLessons returns 0 for empty list`() {
        assertEquals(0, calculator.totalXpFromLessons(emptyList()))
    }

    @Test
    fun `totalXpFromLessons sums only completed lessons`() {
        val lessons = listOf(
            makeLesson("l1", isCompleted = true, xpReward = 10),
            makeLesson("l2", isCompleted = false, xpReward = 20),
            makeLesson("l3", isCompleted = true, xpReward = 15)
        )
        assertEquals(25, calculator.totalXpFromLessons(lessons))
    }

    @Test
    fun `totalXpFromLessons returns 0 when no lessons are completed`() {
        val lessons = listOf(
            makeLesson("l1", isCompleted = false, xpReward = 10),
            makeLesson("l2", isCompleted = false, xpReward = 20)
        )
        assertEquals(0, calculator.totalXpFromLessons(lessons))
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private fun makeCourse(totalLessons: Int, completedLessons: Int) = Course(
        id = "c1",
        title = "Test Course",
        description = "",
        category = "Test",
        totalLessons = totalLessons,
        completedLessons = completedLessons,
        xpReward = 100,
        difficulty = Difficulty.EASY
    )

    private fun makeLesson(id: String, isCompleted: Boolean, xpReward: Int) = Lesson(
        id = id,
        courseId = "c1",
        title = "Lesson $id",
        youtubeUrl = "",
        durationMinutes = 5,
        orderIndex = 0,
        isCompleted = isCompleted,
        xpReward = xpReward
    )
}
