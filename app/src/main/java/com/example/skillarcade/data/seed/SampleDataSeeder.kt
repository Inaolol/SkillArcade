package com.example.skillarcade.data.seed

import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
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
import kotlinx.coroutines.runBlocking

class SampleDataSeeder(
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val goalDao: GoalDao,
    private val trophyDao: TrophyDao,
    private val userProgressDao: UserProgressDao
) : androidx.room.RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("SampleDataSeeder", "Seeding sample data on create...")
        runBlocking { seedData() }
    }

    private suspend fun seedData() {
        try {
            val courses = listOf(
                Course(
                    id = "course_uiux_adv",
                    title = "Advanced UI/UX Masterclass",
                    description = "Master brutalism, neobrutalism, and modern web aesthetics with hands-on projects.",
                    category = "UI/UX",
                    totalLessons = 12,
                    completedLessons = 0,
                    xpReward = 500,
                    difficulty = Difficulty.HARD,
                    thumbnailUrl = "",
                    durationHours = 12,
                    tag = "NEW"
                ),
                Course(
                    id = "course_react_tailwind",
                    title = "React & Tailwind Bootcamp",
                    description = "Build fast, responsive, and beautiful web applications from scratch.",
                    category = "FRONTEND",
                    totalLessons = 24,
                    completedLessons = 0,
                    xpReward = 800,
                    difficulty = Difficulty.MEDIUM,
                    thumbnailUrl = "",
                    durationHours = 24,
                    tag = null
                ),
                Course(
                    id = "course_marketing_guerilla",
                    title = "Guerilla Marketing Tactics",
                    description = "Unconventional strategies to grab attention and drive massive growth.",
                    category = "MARKETING",
                    totalLessons = 8,
                    completedLessons = 0,
                    xpReward = 400,
                    difficulty = Difficulty.EASY,
                    thumbnailUrl = "",
                    durationHours = 8,
                    tag = "POPULAR"
                )
            )
            courseDao.insertAll(courses.map { it.toEntity() })

            // Add some dummy lessons so the course is playable
            val allLessons = courses.flatMap { course ->
                (1..course.totalLessons).map { i ->
                    Lesson(
                        id = "lesson_${course.id}_$i",
                        courseId = course.id,
                        title = "Lesson $i: Introduction",
                        youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                        durationMinutes = 15,
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
                streak = 12, // Match design streak
                lessonsCompleted = 0,
                coursesCompleted = 0,
                lastActiveDate = System.currentTimeMillis()
            )
            userProgressDao.insert(userProgress.toEntity())
            Log.d("SampleDataSeeder", "Seeding complete!")
        } catch (e: Exception) {
            Log.e("SampleDataSeeder", "Seeding failed", e)
        }
    }
}
