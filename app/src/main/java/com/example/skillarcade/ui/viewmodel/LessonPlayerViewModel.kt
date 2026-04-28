package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Lesson
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LessonPlayerUiState(
    val lesson: Lesson? = null,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class LessonPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SkillArcadeRepository
) : ViewModel() {

    private val lessonId: String = savedStateHandle.get<String>("lessonId") ?: ""

    val uiState: StateFlow<LessonPlayerUiState> = repository.getLesson(lessonId)
        .map { lesson ->
            LessonPlayerUiState(
                lesson = lesson,
                isCompleted = lesson?.isCompleted ?: false,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LessonPlayerUiState()
        )

    fun completeLesson() {
        viewModelScope.launch {
            repository.completeLesson(lessonId)
        }
    }
}
