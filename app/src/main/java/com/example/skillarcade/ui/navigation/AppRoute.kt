package com.example.skillarcade.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable data object Splash : AppRoute
    @Serializable data class Onboarding(val page: Int) : AppRoute
    @Serializable data object Home : AppRoute
    @Serializable data object CourseCatalog : AppRoute
    @Serializable data class CourseProgress(val courseId: String) : AppRoute
    @Serializable data class LessonPlayer(val lessonId: String) : AppRoute
    @Serializable data object Goals : AppRoute
    @Serializable data object TrophyRoom : AppRoute
}
