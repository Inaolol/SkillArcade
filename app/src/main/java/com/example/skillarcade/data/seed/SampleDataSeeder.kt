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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleDataSeeder(
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val goalDao: GoalDao,
    private val trophyDao: TrophyDao,
    private val userProgressDao: UserProgressDao
) : androidx.room.RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("SampleDataSeeder", "Seeding sample data...")
            seedData()
        }
    }

    private suspend fun seedData() {
        val courses = listOf(
            Course(
                id = "course_kotlin_basics",
                title = "Kotlin for Beginners",
                description = "Learn the fundamentals of Kotlin programming, from variables and functions to classes and coroutines.",
                category = "Programming",
                totalLessons = 4,
                completedLessons = 0,
                xpReward = 200,
                difficulty = Difficulty.EASY,
                thumbnailUrl = "https://example.com/thumbnails/kotlin_basics.jpg"
            ),
            Course(
                id = "course_ui_design",
                title = "UI/UX Design Essentials",
                description = "Master the principles of modern UI/UX design — from wireframing and prototyping to accessibility and visual hierarchy.",
                category = "Design",
                totalLessons = 3,
                completedLessons = 0,
                xpReward = 250,
                difficulty = Difficulty.MEDIUM,
                thumbnailUrl = "https://example.com/thumbnails/ui_design.jpg"
            ),
            Course(
                id = "course_productivity",
                title = "Deep Work & Productivity",
                description = "Science-backed techniques for focused work, time management, and building habits that stick.",
                category = "Productivity",
                totalLessons = 3,
                completedLessons = 0,
                xpReward = 150,
                difficulty = Difficulty.EASY,
                thumbnailUrl = "https://example.com/thumbnails/productivity.jpg"
            )
        )
        courseDao.insertAll(courses.map { it.toEntity() })

        val lessons = listOf(
            // Kotlin Basics (4 lessons)
            Lesson(
                id = "lesson_kb_1",
                courseId = "course_kotlin_basics",
                title = "Variables, Types & Null Safety",
                youtubeUrl = "https://www.youtube.com/watch?v=F9UC9DY-vIU",
                durationMinutes = 12,
                orderIndex = 0,
                xpReward = 30
            ),
            Lesson(
                id = "lesson_kb_2",
                courseId = "course_kotlin_basics",
                title = "Functions & Lambdas",
                youtubeUrl = "https://www.youtube.com/watch?v=AEkFs3VMDDQ",
                durationMinutes = 15,
                orderIndex = 1,
                xpReward = 30
            ),
            Lesson(
                id = "lesson_kb_3",
                courseId = "course_kotlin_basics",
                title = "Classes & Data Classes",
                youtubeUrl = "https://www.youtube.com/watch?v=Pp4LBwEVpV0",
                durationMinutes = 18,
                orderIndex = 2,
                xpReward = 40
            ),
            Lesson(
                id = "lesson_kb_4",
                courseId = "course_kotlin_basics",
                title = "Coroutines & Flows",
                youtubeUrl = "https://www.youtube.com/watch?v=ShNhJ3wMpvQ",
                durationMinutes = 22,
                orderIndex = 3,
                xpReward = 50
            ),
            // UI/UX Design (3 lessons)
            Lesson(
                id = "lesson_uid_1",
                courseId = "course_ui_design",
                title = "Design Principles & Visual Hierarchy",
                youtubeUrl = "https://www.youtube.com/watch?v=yNDgFK2Jj1E",
                durationMinutes = 14,
                orderIndex = 0,
                xpReward = 40
            ),
            Lesson(
                id = "lesson_uid_2",
                courseId = "course_ui_design",
                title = "Wireframing & Prototyping",
                youtubeUrl = "https://www.youtube.com/watch?v=c9Wg6Cb_YlU",
                durationMinutes = 20,
                orderIndex = 1,
                xpReward = 50
            ),
            Lesson(
                id = "lesson_uid_3",
                courseId = "course_ui_design",
                title = "Colour Theory & Typography",
                youtubeUrl = "https://www.youtube.com/watch?v=_2LLXnUdUIc",
                durationMinutes = 16,
                orderIndex = 2,
                xpReward = 40
            ),
            // Productivity (3 lessons)
            Lesson(
                id = "lesson_prod_1",
                courseId = "course_productivity",
                title = "The Science of Deep Work",
                youtubeUrl = "https://www.youtube.com/watch?v=gTaJhjQHcf8",
                durationMinutes = 10,
                orderIndex = 0,
                xpReward = 20
            ),
            Lesson(
                id = "lesson_prod_2",
                courseId = "course_productivity",
                title = "Time Blocking & Planning",
                youtubeUrl = "https://www.youtube.com/watch?v=oTugjssqOT0",
                durationMinutes = 13,
                orderIndex = 1,
                xpReward = 25
            ),
            Lesson(
                id = "lesson_prod_3",
                courseId = "course_productivity",
                title = "Building Habits That Stick",
                youtubeUrl = "https://www.youtube.com/watch?v=PZ7lDrwYdZc",
                durationMinutes = 11,
                orderIndex = 2,
                xpReward = 25
            )
        )
        lessonDao.insertAll(lessons.map { it.toEntity() })

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
                id = "goal_five_lessons",
                title = "On a Roll",
                description = "Complete 5 lessons.",
                targetValue = 5,
                currentValue = 0,
                xpReward = 100,
                goalType = GoalType.LESSONS_COMPLETED
            ),
            Goal(
                id = "goal_first_course",
                title = "Course Champion",
                description = "Complete your first full course.",
                targetValue = 1,
                currentValue = 0,
                xpReward = 150,
                goalType = GoalType.COURSES_COMPLETED
            ),
            Goal(
                id = "goal_xp_500",
                title = "XP Grinder",
                description = "Earn 500 XP total.",
                targetValue = 500,
                currentValue = 0,
                xpReward = 75,
                goalType = GoalType.XP_EARNED
            ),
            Goal(
                id = "goal_streak_7",
                title = "Week Warrior",
                description = "Maintain a 7-day learning streak.",
                targetValue = 7,
                currentValue = 0,
                xpReward = 200,
                goalType = GoalType.STREAK_DAYS
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
            ),
            Trophy(
                id = "trophy_first_course",
                title = "Course Completer",
                description = "Finished an entire course from start to finish.",
                iconKey = "trophy_graduation",
                rarity = Rarity.COMMON
            ),
            Trophy(
                id = "trophy_streak_3",
                title = "Hat Trick",
                description = "Kept a 3-day learning streak.",
                iconKey = "trophy_flame",
                rarity = Rarity.COMMON
            ),
            Trophy(
                id = "trophy_streak_7",
                title = "Week Warrior",
                description = "Kept a 7-day learning streak.",
                iconKey = "trophy_flame_gold",
                rarity = Rarity.RARE
            ),
            Trophy(
                id = "trophy_xp_500",
                title = "XP Collector",
                description = "Earned 500 XP.",
                iconKey = "trophy_coin",
                rarity = Rarity.RARE
            ),
            Trophy(
                id = "trophy_all_courses",
                title = "Skill Master",
                description = "Completed all available courses.",
                iconKey = "trophy_crown",
                rarity = Rarity.EPIC
            ),
            Trophy(
                id = "trophy_level_10",
                title = "Level Up Legend",
                description = "Reached level 10.",
                iconKey = "trophy_lightning",
                rarity = Rarity.EPIC
            ),
            Trophy(
                id = "trophy_streak_30",
                title = "Unstoppable",
                description = "Maintained a 30-day learning streak.",
                iconKey = "trophy_diamond",
                rarity = Rarity.LEGENDARY
            )
        )
        trophyDao.insertAll(trophies.map { it.toEntity() })

        val userProgress = UserProgress(
            id = UserProgress.SINGLE_USER_ID,
            totalXp = 0,
            level = 1,
            streak = 0,
            lessonsCompleted = 0,
            coursesCompleted = 0,
            lastActiveDate = System.currentTimeMillis()
        )
        userProgressDao.insert(userProgress.toEntity())
    }
}
