package com.example.skillarcade

import android.app.Application
import com.example.skillarcade.data.seed.SampleDataSeeder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class SkillArcadeApplication : Application() {

    @Inject
    lateinit var sampleDataSeeder: SampleDataSeeder

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            sampleDataSeeder.seedIfNeeded()
        }
    }
}
