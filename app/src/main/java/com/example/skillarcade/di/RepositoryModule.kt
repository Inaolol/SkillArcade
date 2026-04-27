package com.example.skillarcade.di

import com.example.skillarcade.data.repository.SkillArcadeRepositoryImpl
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: SkillArcadeRepositoryImpl): SkillArcadeRepository
}
