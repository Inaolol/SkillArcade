package com.example.skillarcade.domain.model

data class Trophy(
    val id: String,
    val title: String,
    val description: String,
    val iconResName: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val rarity: Rarity
)

enum class Rarity { COMMON, RARE, EPIC, LEGENDARY }
