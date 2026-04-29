package com.example.skillarcade.ui.viewmodel

import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.UserProgress
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class HomeDashboardViewModelTest {

    @Test
    fun `buildHomeDashboardUiState keeps a hero course and recommends other courses`() {
        val inProgress = makeCourse("figma", completedLessons = 1, tag = null)
        val popular = makeCourse("javascript", completedLessons = 0, tag = "POPULAR")
        val newest = makeCourse("react", completedLessons = 0, tag = "NEW")

        val state = buildHomeDashboardUiState(
            courses = listOf(inProgress, popular, newest),
            userProgress = UserProgress(streak = 12)
        )

        assertEquals(listOf(inProgress), state.inProgressCourses)
        assertEquals(listOf(popular, newest), state.recommendedCourses)
        assertFalse(state.isLoading)
    }

    @Test
    fun `buildHomeDashboardUiState recommends courses when no course is started`() {
        val courses = listOf(
            makeCourse("figma", completedLessons = 0, tag = null),
            makeCourse("javascript", completedLessons = 0, tag = "POPULAR")
        )

        val state = buildHomeDashboardUiState(
            courses = courses,
            userProgress = UserProgress()
        )

        assertEquals(emptyList<Course>(), state.inProgressCourses)
        assertEquals(courses, state.recommendedCourses)
    }

    private fun makeCourse(
        id: String,
        completedLessons: Int,
        tag: String?
    ) = Course(
        id = id,
        title = "$id course",
        description = "Sample course",
        category = "UI/UX",
        totalLessons = 4,
        completedLessons = completedLessons,
        xpReward = 100,
        difficulty = Difficulty.EASY,
        tag = tag
    )
}
