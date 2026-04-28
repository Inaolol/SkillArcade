package com.example.skillarcade.di

import android.content.Context
import androidx.room.Room
import com.example.skillarcade.data.local.SkillArcadeDatabase
import com.example.skillarcade.data.local.dao.CourseDao
import com.example.skillarcade.data.local.dao.GoalDao
import com.example.skillarcade.data.local.dao.LessonDao
import com.example.skillarcade.data.local.dao.TrophyDao
import com.example.skillarcade.data.local.dao.UserProgressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SkillArcadeDatabase {
        return Room.databaseBuilder(
            context,
            SkillArcadeDatabase::class.java,
            "skillarcade.db"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(db: SkillArcadeDatabase): CourseDao = db.courseDao()

    @Provides
    @Singleton
    fun provideLessonDao(db: SkillArcadeDatabase): LessonDao = db.lessonDao()

    @Provides
    @Singleton
    fun provideGoalDao(db: SkillArcadeDatabase): GoalDao = db.goalDao()

    @Provides
    @Singleton
    fun provideTrophyDao(db: SkillArcadeDatabase): TrophyDao = db.trophyDao()

    @Provides
    @Singleton
    fun provideUserProgressDao(db: SkillArcadeDatabase): UserProgressDao = db.userProgressDao()
}
