package com.example.skillarcade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.domain.model.UserProgress
import com.example.skillarcade.domain.repository.SkillArcadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class CourseCatalogUiState(
    val courses: List<Course> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "ALL",
    val searchQuery: String = "",
    val userProgress: UserProgress? = null
)

@HiltViewModel
class CourseCatalogViewModel @Inject constructor(
    repository: SkillArcadeRepository
) : ViewModel() {

    private val selectedCategory = MutableStateFlow("ALL")
    private val searchQuery = MutableStateFlow("")

    val uiState: StateFlow<CourseCatalogUiState> = combine(
        repository.getCourses(),
        repository.getUserProgress(),
        selectedCategory,
        searchQuery
    ) { courses, progress, category, query ->
        val filteredByCategory = if (category == "ALL") courses 
                                 else courses.filter { it.category.uppercase() == category.uppercase() }
        
        val filteredBySearch = if (query.isBlank()) filteredByCategory
                               else filteredByCategory.filter { 
                                   it.title.contains(query, ignoreCase = true) || 
                                   it.description.contains(query, ignoreCase = true) 
                               }

        val allCategories = listOf("ALL") + courses.map { it.category.uppercase() }.distinct().sorted()

        CourseCatalogUiState(
            courses = filteredBySearch,
            categories = allCategories,
            selectedCategory = category,
            searchQuery = query,
            userProgress = progress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CourseCatalogUiState()
    )

    fun filterByCategory(category: String) {
        selectedCategory.value = category
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
}
