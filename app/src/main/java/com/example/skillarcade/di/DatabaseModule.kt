package com.example.skillarcade.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skillarcade.data.local.SkillArcadeDatabase
import com.example.skillarcade.data.local.dao.CourseDao
import com.example.skillarcade.data.local.dao.GoalDao
import com.example.skillarcade.data.local.dao.LessonDao
import com.example.skillarcade.data.local.dao.TrophyDao
import com.example.skillarcade.data.local.dao.UserProgressDao
import com.example.skillarcade.data.seed.SampleDataSeeder
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
        // `roomDb` is assigned synchronously by `build()` before Room opens the DB file.
        // Room defers the first open to a background thread, so `onCreate` always fires
        // after the assignment completes — making the var-capture safe on Android's JVM.
        var roomDb: SkillArcadeDatabase? = null
        roomDb = Room.databaseBuilder(
            context,
            SkillArcadeDatabase::class.java,
            "skillarcade.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // roomDb is guaranteed to be set before Room calls onCreate
                val database = checkNotNull(roomDb) {
                    "Database instance was null during seeder callback"
                }
                Log.d("DatabaseModule", "Database created; delegating to SampleDataSeeder")
                SampleDataSeeder(
                    courseDao = database.courseDao(),
                    lessonDao = database.lessonDao(),
                    goalDao = database.goalDao(),
                    trophyDao = database.trophyDao(),
                    userProgressDao = database.userProgressDao()
                ).onCreate(db)
            }
        }).build()
        return roomDb
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
