package com.example.skillarcade.data.local

import com.example.skillarcade.data.local.entities.CourseEntity
import com.example.skillarcade.data.local.entities.GoalEntity
import com.example.skillarcade.data.local.entities.LessonEntity
import com.example.skillarcade.data.local.entities.TrophyEntity
import com.example.skillarcade.data.local.entities.UserProgressEntity
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Difficulty
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.GoalType
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.model.Rarity
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress

// Entity → Domain
fun CourseEntity.toDomain(): Course = Course(
    id, title, description, category, totalLessons, completedLessons, xpReward,
    Difficulty.valueOf(difficulty), thumbnailUrl, durationHours, tag
)

fun LessonEntity.toDomain(): Lesson = Lesson(
    id, courseId, title, youtubeUrl, durationMinutes, orderIndex, isCompleted, xpReward
)

fun GoalEntity.toDomain(): Goal = Goal(
    id, title, description, targetValue, currentValue, xpReward, isCompleted, GoalType.valueOf(goalType)
)

fun TrophyEntity.toDomain(): Trophy = Trophy(
    id, title, description, iconKey, isUnlocked, unlockedAt, Rarity.valueOf(rarity)
)

fun UserProgressEntity.toDomain(): UserProgress = UserProgress(
    id, totalXp, level, streak, lessonsCompleted, coursesCompleted, lastActiveDate
)

// Domain → Entity (for seeding)
fun Course.toEntity(): CourseEntity = CourseEntity(
    id, title, description, category, totalLessons, completedLessons, xpReward, difficulty.name, thumbnailUrl, durationHours, tag
)

fun Lesson.toEntity(): LessonEntity = LessonEntity(
    id, courseId, title, youtubeUrl, durationMinutes, orderIndex, isCompleted, xpReward
)

fun Goal.toEntity(): GoalEntity = GoalEntity(
    id, title, description, targetValue, currentValue, xpReward, isCompleted, goalType.name
)

fun Trophy.toEntity(): TrophyEntity = TrophyEntity(
    id, title, description, iconKey, isUnlocked, unlockedAt, rarity.name
)

fun UserProgress.toEntity(): UserProgressEntity = UserProgressEntity(
    id, totalXp, level, streak, lessonsCompleted, coursesCompleted, lastActiveDate
)
