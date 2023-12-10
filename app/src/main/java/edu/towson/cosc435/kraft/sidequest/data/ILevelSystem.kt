package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.data.model.Level

// interface for the level database repository
interface ILevelSystem {

    // function used to get users level from database
    suspend fun getLevel(): Level

    // function used to add exp to users level in database
    suspend fun addExp(level: Level)

    // function used to add a level to the level table in the database
    suspend fun addLevel(level : Level)

}