package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Goal
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class GoalsUiState(
    val goals: List<Goal> = emptyList(),
    val userProgress: UserProgress? = null
)

@HiltViewModel
class GoalsViewModel @Inject constructor(
    repository: SkillArcadeRepository
) : ViewModel() {

    val uiState: StateFlow<GoalsUiState> = combine(
        repository.getGoals(),
        repository.getUserProgress()
    ) { goals, userProgress ->
        GoalsUiState(
            goals = goals,
            userProgress = userProgress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GoalsUiState()
    )
}
