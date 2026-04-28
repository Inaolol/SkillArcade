package com.example.skillarcade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.skillarcade.domain.model.Course
import com.example.skillarcade.ui.components.ArcadeButton
import com.example.skillarcade.ui.components.ArcadeChip
import com.example.skillarcade.ui.theme.ArcadeColors
import com.example.skillarcade.ui.theme.Epilogue
import com.example.skillarcade.ui.theme.arcadeBorderShadow
import com.example.skillarcade.ui.viewmodel.CourseCatalogViewModel

@Composable
fun CourseCatalogScreen(onOpenCourse: (courseId: String) -> Unit) {
    val vm: CourseCatalogViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2)), // Match design cream background
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            TopBar(streak = uiState.userProgress?.streak ?: 0)
        }
        item {
            CatalogHeader()
        }
        item {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = vm::onSearchQueryChanged
            )
        }
        item {
            CategoryFilters(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onSelect = vm::filterByCategory
            )
        }

        if (uiState.courses.isEmpty()) {
            item {
                EmptyState(query = uiState.searchQuery, category = uiState.selectedCategory)
            }
        } else {
            items(uiState.courses, key = { it.id }) { course ->
                CourseCatalogCard(
                    course = course,
                    onOpenCourse = { onOpenCourse(course.id) }
                )
            }
            item {
                LoadMoreButton()
            }
        }
    }
}

@Composable
private fun TopBar(streak: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SKILLARCADE",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Epilogue,
                fontWeight = FontWeight.Bold
            ),
            color = ArcadeColors.InkBlack
        )
        Box(
            modifier = Modifier
                .arcadeBorderShadow(
                    cornerRadius = 50.dp,
                    shadowOffset = 2.dp,
                    backgroundColor = Color.White
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "🔥", fontSize = 16.sp)
                Text(
                    text = "$streak",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = ArcadeColors.InkBlack
                )
            }
        }
    }
}

@Composable
private fun CatalogHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "COURSE CATALOG",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = Epilogue,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1).sp
            ),
            color = ArcadeColors.InkBlack
        )
    }
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(cornerRadius = 12.dp, shadowOffset = 2.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { 
                Text(
                    "SEARCH FOR SKILLS...", 
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = ArcadeColors.InkBlack.copy(alpha = 0.5f)
                ) 
            },
            leadingIcon = { 
                Icon(
                    imageVector = Icons.Default.Search, 
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = ArcadeColors.InkBlack
                ) 
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@Composable
private fun CategoryFilters(
    categories: List<String>,
    selectedCategory: String,
    onSelect: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories) { category ->
            ArcadeChip(
                text = category,
                color = if (selectedCategory == category) ArcadeColors.PrimaryYellow
                        else Color.White,
                modifier = Modifier.clickable { onSelect(category) }
            )
        }
    }
}

@Composable
private fun CourseCatalogCard(
    course: Course,
    onOpenCourse: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = Color.White)
            .clickable(onClick = onOpenCourse)
    ) {
        CourseThumbnail(course = course)
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = course.title.uppercase(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Epilogue,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                ),
                color = ArcadeColors.InkBlack,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Dotted Separator
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .drawBehind {
                        val pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        drawLine(
                            color = ArcadeColors.InkBlack.copy(alpha = 0.2f),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = pathEffect
                        )
                    }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "🕒", fontSize = 14.sp)
                    Text(
                        text = "${course.durationHours}h",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = ArcadeColors.InkBlack
                    )
                }
                ArcadeButton(
                    text = "ENROLL NOW",
                    onClick = onOpenCourse,
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}

@Composable
private fun CourseThumbnail(course: Course) {
    val bgColor = when (course.category.uppercase()) {
        "UI/UX" -> Color(0xFF334155)
        "FRONTEND" -> Color(0xFF5A2A27)
        "MARKETING" -> Color(0xFF1B4332)
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(bgColor, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    ) {
        if (course.thumbnailUrl.isNotBlank()) {
            AsyncImage(
                model = course.thumbnailUrl,
                contentDescription = "${course.title} video thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.28f))
            )
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "▶",
                    color = ArcadeColors.InkBlack,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(60.dp))
            )
        }
        
        if (course.tag != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(
                        if (course.tag == "NEW") Color(0xFF4ADE80) else ArcadeColors.PrimaryYellow,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = course.tag,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = ArcadeColors.InkBlack
                )
            }
        }
    }
}

@Composable
private fun EmptyState(query: String, category: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .arcadeBorderShadow(backgroundColor = Color.White)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No courses found",
                style = MaterialTheme.typography.headlineMedium,
                color = ArcadeColors.InkBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LoadMoreButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .arcadeBorderShadow(cornerRadius = 12.dp, shadowOffset = 2.dp, backgroundColor = Color.White)
                .clickable { /* Load more */ }
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "LOAD MORE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = Epilogue,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = ArcadeColors.InkBlack
                )
                Text(text = "🔄", fontSize = 16.sp)
            }
        }
    }
}
