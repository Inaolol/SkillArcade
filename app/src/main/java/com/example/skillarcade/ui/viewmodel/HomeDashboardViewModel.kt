package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeDashboardUiState(
    val userProgress: UserProgress? = null,
    val inProgressCourses: List<Course> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeDashboardViewModel @Inject constructor(
    private val repository: SkillArcadeRepository
) : ViewModel() {

    val uiState: StateFlow<HomeDashboardUiState> = combine(
        repository.getCourses(),
        repository.getUserProgress()
    ) { courses, userProgress ->
        HomeDashboardUiState(
            userProgress = userProgress,
            inProgressCourses = courses
                .filter { it.completedLessons > 0 && it.completedLessons < it.totalLessons }
                .take(3),
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeDashboardUiState(isLoading = true)
    )
}
