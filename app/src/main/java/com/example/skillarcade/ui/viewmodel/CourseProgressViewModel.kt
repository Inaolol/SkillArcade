package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class CourseProgressUiState(
    val course: Course? = null,
    val lessons: List<Lesson> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class CourseProgressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: SkillArcadeRepository
) : ViewModel() {

    private val courseId: String = checkNotNull(savedStateHandle.get<String>("courseId")) {
        "CourseProgressViewModel requires a courseId navigation argument"
    }

    val uiState: StateFlow<CourseProgressUiState> = combine(
        repository.getCourse(courseId),
        repository.getLessons(courseId)
    ) { course, lessons ->
        CourseProgressUiState(
            course = course,
            lessons = lessons.sortedBy { it.orderIndex },
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CourseProgressUiState(isLoading = true)
    )
}
