package edu.towson.cosc435.kraft.sidequest.data.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
@Entity(tableName = "stats")
data class Stats (
    @PrimaryKey
    var passedEasy: Int,
    var passedMedium: Int,
    var passedHard: Int,
    var passedTotal: Int,

    var failedEasy: Int,
    var failedMedium: Int,
    var failedHard: Int,
    var failedTotal: Int,

    var totalQuests: Int,

    var longestStreak: Int,
    var currentStreak: Int,
)