package edu.towson.cosc435.kraft.sidequest.data.model

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import java.util.Date

data class Quest(
    val id: String,
    val category: String,
    val description: String,
    val date: String,
    val time: String,
    val exp: DifficultyEnum,
    val status: StatusEnum
)
