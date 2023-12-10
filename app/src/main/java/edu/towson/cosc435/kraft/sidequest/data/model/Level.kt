package edu.towson.cosc435.kraft.sidequest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// adds an entry in the table in the rooms database called levels based on the data class below
@Entity(tableName = "levels")
data class Level(
    @PrimaryKey // specifies that id is the primary key for the levels table
    val id: Int, // id is used to give the table a primary key
    var level: Long, // level holds the current level of the user
    var currentExp: Long, // currentExp holds the current exp the user has earned for their level
    var expTillLevelUp: Long // expTillLevelUp holds the amount of exp the user must earn to level up
)