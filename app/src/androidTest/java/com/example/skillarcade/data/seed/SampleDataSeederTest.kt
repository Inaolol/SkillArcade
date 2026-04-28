package com.example.skillarcade.data.seed

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skillarcade.data.local.SkillArcadeDatabase
import com.example.skillarcade.domain.model.UserProgress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleDataSeederTest {

    private lateinit var db: SkillArcadeDatabase
    private lateinit var seeder: SampleDataSeeder

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SkillArcadeDatabase::class.java
        ).allowMainThreadQueries().build()

        seeder = SampleDataSeeder(
            db = db,
            courseDao = db.courseDao(),
            lessonDao = db.lessonDao(),
            goalDao = db.goalDao(),
            trophyDao = db.trophyDao(),
            userProgressDao = db.userProgressDao()
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun seedIfNeeded_populatesEmptyDatabase() = runTest {
        seeder.seedIfNeeded()

        val courses = db.courseDao().getAll().first()
        val goals = db.goalDao().getAll().first()
        val trophies = db.trophyDao().getAll().first()
        val userProgress = db.userProgressDao().getById(UserProgress.SINGLE_USER_ID).first()

        assertEquals(8, courses.size)
        assertEquals(2, goals.size)
        assertEquals(1, trophies.size)
        assertNotNull(userProgress)
        assertTrue(courses.all { it.thumbnailUrl.startsWith("https://img.youtube.com/vi/") })
        assertTrue(courses.all { course ->
            db.lessonDao().getByCourse(course.id).first().size == course.totalLessons
        })
    }

    @Test
    fun seedIfNeeded_isIdempotent() = runTest {
        seeder.seedIfNeeded()
        seeder.seedIfNeeded()

        assertEquals(8, db.courseDao().countCourses())
        assertEquals(32, db.lessonDao().countLessons())
        assertEquals(2, db.goalDao().countGoals())
        assertEquals(1, db.trophyDao().countTrophies())
    }
}
