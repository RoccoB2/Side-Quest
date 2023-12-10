package edu.towson.cosc435.kraft.sidequest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum

// adds an entry in the table in the rooms database called quests based on the data class below
@Entity(tableName = "quests")
data class Quest(
    @PrimaryKey(autoGenerate = true) // generates a unique key for the primary key of the quest table
    val id: Int, // int for holding primary key
    val category: String, // string for holding the category the quest belongs to
    val description: String, // string for holding the description of the quest
    val date: String, // string for holding the date the quest is due
    val time: String, // string for holding the time the quest is due
    val exp: DifficultyEnum, // DifficultyEnum for specifying if the quest is easy, medium, or hard
    val status: StatusEnum, // StatusEnum for specifying if the quest is pending, passed, or failed
    val header: String, // string for holding the header of a quest
)
