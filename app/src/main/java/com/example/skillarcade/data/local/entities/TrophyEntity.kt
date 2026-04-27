package com.example.skillarcade.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trophies")
data class TrophyEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconKey: String,        // was iconResName in old spec — use iconKey
    val isUnlocked: Boolean,
    val unlockedAt: Long?,
    val rarity: String         // store enum name as String
)
