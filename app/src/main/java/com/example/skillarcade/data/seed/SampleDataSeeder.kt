package com.example.skillarcade.data.seed

import android.util.Log
import androidx.room.withTransaction
import com.example.skillarcade.data.local.SkillArcadeDatabase
import com.example.skillarcade.data.local.dao.CourseDao
import com.example.skillarcade.data.local.dao.GoalDao
import com.example.skillarcade.data.local.dao.LessonDao
import com.example.skillarcade.data.local.dao.TrophyDao
import com.example.skillarcade.data.local.dao.UserProgressDao
import com.example.skillarcade.data.local.toEntity
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.GoalType
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.model.Rarity
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataSeeder @Inject constructor(
    private val db: SkillArcadeDatabase,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val goalDao: GoalDao,
    private val trophyDao: TrophyDao,
    private val userProgressDao: UserProgressDao
) {
    private companion object {
        const val SAMPLE_COURSE_COUNT = 8
    }

    suspend fun seedIfNeeded() {
        try {
            db.withTransaction {
                if (courseDao.countCourses() >= SAMPLE_COURSE_COUNT) {
                    Log.d("SampleDataSeeder", "Sample data already present; skipping seed.")
                    return@withTransaction
                }
                Log.d("SampleDataSeeder", "Seeding sample data...")
                seedData()
            }
            Log.d("SampleDataSeeder", "Seeding complete!")
        } catch (e: Exception) {
            Log.e("SampleDataSeeder", "Seeding failed", e)
        }
    }

    private suspend fun seedData() {
        val courses = listOf(
            Course(
                id = "course_uiux_adv",
                title = "Advanced UI/UX Masterclass",
                description = "Master brutalism, neobrutalism, and modern web aesthetics with hands-on projects.",
                category = "UI/UX",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 500,
                difficulty = Difficulty.HARD,
                thumbnailUrl = youtubeThumbnail("c9Wg6Cb_YlU"),
                durationHours = 2,
                tag = "NEW"
            ),
            Course(
                id = "course_figma_ui_foundations",
                title = "Figma UI Design Foundations",
                description = "Design polished interfaces in Figma with layout, components, color, and practical UI workflows.",
                category = "UI/UX",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 450,
                difficulty = Difficulty.EASY,
                thumbnailUrl = youtubeThumbnail("kbZejnPXyLM"),
                durationHours = 4,
                tag = null
            ),
            Course(
                id = "course_javascript_pro",
                title = "JavaScript Beginner to Pro",
                description = "Build a strong JavaScript foundation with browser projects, DOM skills, testing, and async code.",
                category = "FRONTEND",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 700,
                difficulty = Difficulty.MEDIUM,
                thumbnailUrl = youtubeThumbnail("EerdGm-ehJQ"),
                durationHours = 22,
                tag = "POPULAR"
            ),
            Course(
                id = "course_react_tailwind",
                title = "React 19 App Builder",
                description = "Master React fundamentals by building a modern app with components, hooks, effects, and search UX.",
                category = "FRONTEND",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 800,
                difficulty = Difficulty.MEDIUM,
                thumbnailUrl = youtubeThumbnail("dCLhUialKPQ"),
                durationHours = 2,
                tag = "NEW"
            ),
            Course(
                id = "course_tailwind_v4",
                title = "Tailwind CSS v4 Mastery",
                description = "Use utility-first CSS to build responsive, sharp, production-ready interfaces quickly.",
                category = "FRONTEND",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 500,
                difficulty = Difficulty.EASY,
                thumbnailUrl = youtubeThumbnail("6biMWgD6_JY"),
                durationHours = 1,
                tag = null
            ),
            Course(
                id = "course_android_beginners",
                title = "Android Development for Beginners",
                description = "Start Android development with project setup, app structure, UI basics, and real mobile workflows.",
                category = "MOBILE",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 900,
                difficulty = Difficulty.HARD,
                thumbnailUrl = youtubeThumbnail("fis26HvvDII"),
                durationHours = 15,
                tag = "POPULAR"
            ),
            Course(
                id = "course_react_native_stack",
                title = "React Native Full Stack App",
                description = "Build a full stack mobile app with React Native, navigation, API data, and app-ready patterns.",
                category = "MOBILE",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 750,
                difficulty = Difficulty.MEDIUM,
                thumbnailUrl = youtubeThumbnail("f8Z9JyB2EIE"),
                durationHours = 4,
                tag = "NEW"
            ),
            Course(
                id = "course_marketing_guerilla",
                title = "Digital Marketing Roadmap",
                description = "Plan a modern digital marketing path across content, social, SEO, ads, analytics, and strategy.",
                category = "MARKETING",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 400,
                difficulty = Difficulty.EASY,
                thumbnailUrl = youtubeThumbnail("KZLroOQKT-g"),
                durationHours = 2,
                tag = "POPULAR"
            )
        )
        courseDao.insertAll(courses.map { it.toEntity() })

        val allLessons = courses.flatMap { course ->
            (1..course.totalLessons).map { i ->
                Lesson(
                    id = "lesson_${course.id}_$i",
                    courseId = course.id,
                    title = lessonTitle(i),
                    youtubeUrl = course.videoUrl(),
                    durationMinutes = 20,
                    orderIndex = i - 1,
                    xpReward = course.xpReward / course.totalLessons
                )
            }
        }
        lessonDao.insertAll(allLessons.map { it.toEntity() })

        val goals = listOf(
            Goal(
                id = "goal_first_lesson",
                title = "First Step",
                description = "Complete your first lesson.",
                targetValue = 1,
                currentValue = 0,
                xpReward = 50,
                goalType = GoalType.LESSONS_COMPLETED
            ),
            Goal(
                id = "goal_xp_500",
                title = "XP Grinder",
                description = "Earn 500 XP total.",
                targetValue = 500,
                currentValue = 0,
                xpReward = 75,
                goalType = GoalType.XP_EARNED
            )
        )
        goalDao.insertAll(goals.map { it.toEntity() })

        val trophies = listOf(
            Trophy(
                id = "trophy_first_lesson",
                title = "Eager Learner",
                description = "Completed your very first lesson.",
                iconKey = "trophy_star",
                rarity = Rarity.COMMON
            )
        )
        trophyDao.insertAll(trophies.map { it.toEntity() })

        val userProgress = UserProgress(
            id = UserProgress.SINGLE_USER_ID,
            totalXp = 0,
            level = 1,
            streak = 12,
            lessonsCompleted = 0,
            coursesCompleted = 0,
            lastActiveDate = System.currentTimeMillis()
        )
        userProgressDao.insert(userProgress.toEntity())
    }

    private fun youtubeThumbnail(videoId: String): String =
        "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

    private fun Course.videoUrl(): String =
        thumbnailUrl.substringAfter("/vi/").substringBefore("/hqdefault.jpg")
            .let { videoId -> "https://www.youtube.com/watch?v=$videoId" }

    private fun lessonTitle(lessonNumber: Int): String {
        val topic = when (lessonNumber) {
            1 -> "Core Concepts"
            2 -> "Guided Build"
            3 -> "Practical Workflow"
            else -> "Final Project"
        }
        return "Lesson $lessonNumber: $topic"
    }
}
