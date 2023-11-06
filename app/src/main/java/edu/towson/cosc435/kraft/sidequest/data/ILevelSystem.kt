package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Level

interface ILevelSystem {
    fun getLevelObj(): Level
    fun getLevel(): Long
    fun getCurrentExp(): Long
    fun getExpTillLevelUp(): Long
    fun addExp(dificulty: DifficultyEnum)
    fun checkLevelUp()
    fun calculateExpForNextLevel()


}