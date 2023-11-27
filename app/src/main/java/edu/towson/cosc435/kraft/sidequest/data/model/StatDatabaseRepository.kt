package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.IStatRepository
import edu.towson.cosc435.kraft.sidequest.data.QuestsDatabase
import edu.towson.cosc435.kraft.sidequest.data.StatsDatabase

class StatDatabaseRepository(app: Application): IStatRepository  {
    private val db: StatsDatabase

    init {
        db = Room.databaseBuilder(app, StatsDatabase::class.java, "stats.db").build()
    }

    override suspend fun getStats(): Stats {
        return db.statsDao().getStats()
    }

    override suspend fun addStats(){
        db.statsDao().addStats(Stats(0,0,0,0,0,0,0,0,0,0,0))
    }

    override suspend fun incrementpassedEasy(stat:Stats, passedEasy : Int) {
        db.statsDao().updateStat(stat.copy(passedEasy = passedEasy))
    }

    override suspend fun incrementpassedMedium(stat:Stats, passedMedium : Int) {
        db.statsDao().updateStat(stat.copy(passedMedium = passedMedium))
    }

    override suspend fun incrementpassedHard(stat:Stats, passedHard : Int) {
        db.statsDao().updateStat(stat.copy(passedHard = passedHard))
    }

    override suspend fun incrementpassedTotal(stat:Stats, passedTotal : Int) {
        db.statsDao().updateStat(stat.copy(passedTotal = passedTotal))
    }

    override suspend fun incrementfailedEasy(stat:Stats, failedEasy : Int) {
        db.statsDao().updateStat(stat.copy(failedEasy = failedEasy))
    }

    override suspend fun incrementfailedMedium(stat:Stats, failedMedium : Int) {
        db.statsDao().updateStat(stat.copy(failedMedium = failedMedium))
    }

    override suspend fun incrementfailedHard(stat:Stats, failedHard : Int) {
        db.statsDao().updateStat(stat.copy(failedHard = failedHard))
    }

    override suspend fun incrementfailedTotal(stat:Stats, failedTotal : Int) {
        db.statsDao().updateStat(stat.copy(failedTotal = failedTotal))
    }

    override suspend fun incrementtotalQuests(stat:Stats, totalQuests : Int) {
        db.statsDao().updateStat(stat.copy(totalQuests = totalQuests))
    }

    override suspend fun incrementlongestStreak(stat:Stats, longestStreak : Int, currentStreak: Int) {
        if(stat.currentStreak > stat.longestStreak)
        db.statsDao().updateStat(stat.copy(longestStreak = currentStreak))
    }

    override suspend fun incrementcurrentStreak(stat:Stats, currentStreak : Int) {
        db.statsDao().updateStat(stat.copy(currentStreak = currentStreak))
    }

    override suspend fun resetcurrentStreak(stat:Stats, currentStreak: Int) {
        db.statsDao().updateStat(stat.copy(currentStreak = currentStreak))
    }

}

//class QuestDatabaseRepository(app: Application) : IQuestRepository {
//
//    private val db: QuestsDatabase
//
//    init {
//        db = Room.databaseBuilder(app, QuestsDatabase::class.java, "quests.db").build()
//    }
//    override suspend fun getQuests(): List<Quest> {
//        return db.questsDao().getQuests()
//    }
//
//    override suspend fun addQuest(quest: Quest) {
//        db.questsDao().addQuest(quest)
//    }
//
//    override suspend fun toggleStatus(quest: Quest, status: StatusEnum) {
//        db.questsDao().updateQuest(quest.copy(status = status))
//    }
//
//    override suspend fun deleteQuest(quest: Quest) {
//        db.questsDao().deleteQuest(quest)
//    }
//
////    override fun updateQuestList(quests: List<Quest>) {
////
////    }
//
//}