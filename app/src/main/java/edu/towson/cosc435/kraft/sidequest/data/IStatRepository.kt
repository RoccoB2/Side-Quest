package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.Stats

interface IStatRepository {
    suspend fun getStats(): Stats
    suspend fun addStats()
    suspend fun incrementpassedEasy(stat:Stats, num : Int)
    suspend fun incrementpassedMedium(stat:Stats, num : Int)
    suspend fun incrementpassedHard(stat:Stats, num : Int)
    suspend fun incrementpassedTotal(stat:Stats, num : Int)
    suspend fun incrementfailedEasy(stat:Stats, num : Int)
    suspend fun incrementfailedMedium(stat:Stats, num : Int)
    suspend fun incrementfailedHard(stat:Stats, num : Int)
    suspend fun incrementfailedTotal(stat:Stats, num : Int)
    suspend fun incrementtotalQuests(stat:Stats, num : Int)
    suspend fun incrementlongestStreak(stat:Stats, num : Int, currentStreak: Int)
    suspend fun incrementcurrentStreak(stat:Stats, num : Int)

    suspend fun resetcurrentStreak(stat:Stats, num : Int)

}
