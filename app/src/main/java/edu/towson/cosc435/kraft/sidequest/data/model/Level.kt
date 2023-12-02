package edu.towson.cosc435.kraft.sidequest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "levels")
data class Level(
    @PrimaryKey
    val id: Int,
    var level: Long,
    var currentExp: Long,
    var expTillLevelUp: Long
)