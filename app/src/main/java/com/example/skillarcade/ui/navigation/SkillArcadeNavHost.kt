package com.example.skillarcade.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.skillarcade.ui.components.BottomNavBar
import com.example.skillarcade.ui.screens.CourseCatalogScreen
import com.example.skillarcade.ui.screens.CourseProgressScreen
import com.example.skillarcade.ui.screens.GoalsScreen
import com.example.skillarcade.ui.screens.HomeDashboardScreen
import com.example.skillarcade.ui.screens.LessonPlayerScreen
import com.example.skillarcade.ui.screens.OnboardingScreen
import com.example.skillarcade.ui.screens.SplashScreen
import com.example.skillarcade.ui.screens.TrophyRoomScreen

@Composable
fun SkillArcadeNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
        modifier = modifier
    ) {
        // Full-screen routes (no bottom nav)
        composable<AppRoute.Splash> {
            SplashScreen(onNavigateToOnboarding = { navController.navigate(AppRoute.Onboarding(0)) })
        }
        composable<AppRoute.Onboarding> { backStackEntry ->
            val route: AppRoute.Onboarding = backStackEntry.toRoute()
            OnboardingScreen(
                page = route.page,
                onNext = {
                    if (route.page < 3) navController.navigate(AppRoute.Onboarding(route.page + 1))
                    else navController.navigate(AppRoute.Home) { popUpTo(AppRoute.Splash) { inclusive = true } }
                }
            )
        }
        composable<AppRoute.CourseProgress> { backStackEntry ->
            val route: AppRoute.CourseProgress = backStackEntry.toRoute()
            CourseProgressScreen(
                courseId = route.courseId,
                onOpenLesson = { lessonId -> navController.navigate(AppRoute.LessonPlayer(lessonId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable<AppRoute.LessonPlayer> { backStackEntry ->
            val route: AppRoute.LessonPlayer = backStackEntry.toRoute()
            LessonPlayerScreen(
                lessonId = route.lessonId,
                onBack = { navController.popBackStack() }
            )
        }

        // Bottom-nav scaffold routes
        composable<AppRoute.Home> {
            MainScaffold(navController) {
                HomeDashboardScreen(
                    onOpenCourse = { courseId -> navController.navigate(AppRoute.CourseProgress(courseId)) }
                )
            }
        }
        composable<AppRoute.CourseCatalog> {
            MainScaffold(navController) {
                CourseCatalogScreen(
                    onOpenCourse = { courseId -> navController.navigate(AppRoute.CourseProgress(courseId)) }
                )
            }
        }
        composable<AppRoute.Goals> {
            MainScaffold(navController) {
                GoalsScreen(onNavigateToCourses = { navController.navigate(AppRoute.CourseCatalog) })
            }
        }
        composable<AppRoute.TrophyRoom> {
            MainScaffold(navController) {
                TrophyRoomScreen(onNavigateToHome = { navController.navigate(AppRoute.Home) })
            }
        }
    }
}

@Composable
private fun MainScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTabRoute = when {
        navBackStackEntry?.destination?.hasRoute(AppRoute.Home::class) == true -> "home"
        navBackStackEntry?.destination?.hasRoute(AppRoute.CourseCatalog::class) == true -> "course_catalog"
        navBackStackEntry?.destination?.hasRoute(AppRoute.Goals::class) == true -> "goals"
        navBackStackEntry?.destination?.hasRoute(AppRoute.TrophyRoom::class) == true -> "trophy_room"
        else -> null
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentTabRoute,
                onNavigate = { route ->
                    navController.navigate(
                        when (route) {
                            "home" -> AppRoute.Home
                            "course_catalog" -> AppRoute.CourseCatalog
                            "goals" -> AppRoute.Goals
                            "trophy_room" -> AppRoute.TrophyRoom
                            else -> AppRoute.Home
                        }
                    ) {
                        popUpTo(AppRoute.Home) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
