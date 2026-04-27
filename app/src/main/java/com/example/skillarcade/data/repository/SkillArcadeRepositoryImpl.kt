package com.example.skillarcade.data.repository

import com.example.skillarcade.data.local.dao.CourseDao
import com.example.skillarcade.data.local.dao.GoalDao
import com.example.skillarcade.data.local.dao.LessonDao
import com.example.skillarcade.data.local.dao.TrophyDao
import com.example.skillarcade.data.local.dao.UserProgressDao
import com.example.skillarcade.data.local.entities.CourseEntity
import com.example.skillarcade.data.local.entities.GoalEntity
import com.example.skillarcade.data.local.entities.LessonEntity
import com.example.skillarcade.data.local.entities.TrophyEntity
import com.example.skillarcade.data.local.toDomain
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkillArcadeRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao,
    private val goalDao: GoalDao,
    private val trophyDao: TrophyDao,
    private val userProgressDao: UserProgressDao
) : SkillArcadeRepository {

    override fun getCourses(): Flow<List<Course>> =
        courseDao.getAll().map { it.map(CourseEntity::toDomain) }

    override fun getCourse(courseId: String): Flow<Course?> =
        courseDao.getById(courseId).map { it?.toDomain() }

    override fun getLessons(courseId: String): Flow<List<Lesson>> =
        lessonDao.getByCourse(courseId).map { it.map(LessonEntity::toDomain) }

    override fun getLesson(lessonId: String): Flow<Lesson?> =
        lessonDao.getById(lessonId).map { it?.toDomain() }

    override fun getGoals(): Flow<List<Goal>> =
        goalDao.getAll().map { it.map(GoalEntity::toDomain) }

    override fun getTrophies(): Flow<List<Trophy>> =
        trophyDao.getAll().map { it.map(TrophyEntity::toDomain) }

    override fun getUserProgress(): Flow<UserProgress> =
        userProgressDao.getById(UserProgress.SINGLE_USER_ID)
            .map { it?.toDomain() ?: UserProgress() }

    override suspend fun completeLesson(lessonId: String) {
        lessonDao.markCompleted(lessonId)
        val lesson = lessonDao.getById(lessonId).first() ?: return
        courseDao.incrementCompleted(lesson.courseId)
        userProgressDao.incrementLessonsCompleted(UserProgress.SINGLE_USER_ID)
        addXp(lesson.xpReward)
        // Check if course is now fully complete
        val course = courseDao.getById(lesson.courseId).first()
        if (course != null && course.completedLessons >= course.totalLessons) {
            userProgressDao.incrementCoursesCompleted(UserProgress.SINGLE_USER_ID)
        }
    }

    override suspend fun updateGoalProgress(goalId: String, value: Int) {
        goalDao.updateProgress(goalId, value)
    }

    override suspend fun unlockTrophy(trophyId: String) {
        trophyDao.unlock(trophyId, System.currentTimeMillis())
    }

    override suspend fun addXp(amount: Int) {
        userProgressDao.addXp(UserProgress.SINGLE_USER_ID, amount, System.currentTimeMillis())
    }
}
