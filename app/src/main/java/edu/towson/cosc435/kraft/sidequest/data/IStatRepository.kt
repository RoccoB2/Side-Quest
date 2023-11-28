package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.Stats

interface IStatRepository {
    suspend fun getStats(): Stats
    suspend fun addStats()
    suspend fun updateStats(stat: Stats)

}
