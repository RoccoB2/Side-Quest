package edu.towson.cosc435.kraft.sidequest.data.model

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import java.util.Date
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class Quest(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
    val description: String,
    val date: String,
    val time: String,
    val exp: DifficultyEnum,
    val status: StatusEnum,
    val header: String,
)
