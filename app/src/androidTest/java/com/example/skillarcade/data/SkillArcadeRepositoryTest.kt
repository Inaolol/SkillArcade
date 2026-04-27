package com.example.skillarcade.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skillarcade.data.local.SkillArcadeDatabase
import com.example.skillarcade.data.local.entities.CourseEntity
import com.example.skillarcade.data.repository.SkillArcadeRepositoryImpl
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
class SkillArcadeRepositoryTest {

    private lateinit var db: SkillArcadeDatabase
    private lateinit var repository: SkillArcadeRepositoryImpl

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SkillArcadeDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = SkillArcadeRepositoryImpl(
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
    fun getCourses_returnsCourseAfterInsert() = runTest {
        val course = CourseEntity(
            id = "test-course-1",
            title = "Test Course",
            description = "A course for testing",
            category = "Test",
            totalLessons = 5,
            completedLessons = 0,
            xpReward = 100,
            difficulty = "EASY",
            thumbnailUrl = ""
        )

        db.courseDao().insertAll(listOf(course))

        val courses = repository.getCourses().first()
        assertTrue("Expected at least one course, got ${courses.size}", courses.isNotEmpty())
        assertTrue(courses.any { it.id == "test-course-1" })
    }

    @Test
    fun getCourses_returnsEmptyListWhenNoCourses() = runTest {
        val courses = repository.getCourses().first()
        assertTrue("Expected empty list", courses.isEmpty())
    }

    @Test
    fun getCourse_returnsCorrectCourse() = runTest {
        val course = CourseEntity(
            id = "course-abc",
            title = "Specific Course",
            description = "Details here",
            category = "Android",
            totalLessons = 10,
            completedLessons = 3,
            xpReward = 200,
            difficulty = "MEDIUM",
            thumbnailUrl = ""
        )
        db.courseDao().insertAll(listOf(course))

        val fetched = repository.getCourse("course-abc").first()
        assertNotNull(fetched)
        assertEquals("Specific Course", fetched!!.title)
        assertEquals(10, fetched.totalLessons)
    }
}
