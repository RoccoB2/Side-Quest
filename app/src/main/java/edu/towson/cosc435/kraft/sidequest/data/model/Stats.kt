package edu.towson.cosc435.kraft.sidequest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// adds an entry in the table in the rooms database called stats based on the data class below
@Entity(tableName = "stats")
data class Stats (
    @PrimaryKey // makes the id below the primary key of the table (because we only have one row this value does not need a uniquely generated value however still needs to be an unchanged value for easy functionality)
    val id: Int, // int to hold id of the row
    var passedEasy: Int, // int to hold the current number of easy quests passed
    var passedMedium: Int, // int to hold the current number of medium quests passed
    var passedHard: Int, // int to hold the current number of hard quests passed
    var passedTotal: Int, // int to hold the current number of all quests passed
    var failedEasy: Int, // int to hold the current number of easy quests failed
    var failedMedium: Int, // int to hold the current number of medium quests failed
    var failedHard: Int, // int to hold the current number of hard quests failed
    var failedTotal: Int, // int to hold the current number of all quests failed
    var totalQuests: Int, // int to hold the current number of all quests completed (passed or failed)
    var longestStreak: Int, // int to hold the current number of quests passed in a row
    var currentStreak: Int, // int to hold the users highest amount of quests passed in a row
)