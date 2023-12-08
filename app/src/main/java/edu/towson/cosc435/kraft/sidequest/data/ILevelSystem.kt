package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Level

interface ILevelSystem {
    //fun getLevelObj(): Level
    suspend fun getLevel(): Level
    //fun getCurrentExp(): Long
    //fun getExpTillLevelUp(): Long
    suspend fun addExp(level: Level)
    //fun checkLevelUp()
    //fun calculateExpForNextLevel()
    suspend fun addLevel(level : Level)
}