package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Trophy
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TrophyRoomUiState(
    val trophies: List<Trophy> = emptyList(),
    val userProgress: UserProgress? = null
)

@HiltViewModel
class TrophyRoomViewModel @Inject constructor(
    repository: SkillArcadeRepository
) : ViewModel() {

    val uiState: StateFlow<TrophyRoomUiState> = combine(
        repository.getTrophies(),
        repository.getUserProgress()
    ) { trophies, userProgress ->
        TrophyRoomUiState(
            trophies = trophies,
            userProgress = userProgress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TrophyRoomUiState()
    )
}
