package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.LevelDatabase

class LevelDatabaseRepository(app: Application): ILevelSystem {
    private val db: LevelDatabase

    init {
        db = Room.databaseBuilder(app, LevelDatabase::class.java, "level.db").build()
    }


    override suspend fun getLevel(): Level {
        return db.levelDao().getLevel()
    }

    override suspend fun addExp(level : Level) {
        db.levelDao().updateLevel(level)
    }

    override suspend fun addLevel(level : Level) {
        db.levelDao().addLevel(level)
    }



}
//package edu.towson.cosc435.kraft.sidequest.data.model
//
//import android.app.Application
//import androidx.room.Room

//class StatDatabaseRepository(app: Application): IStatRepository  {
//    private val db: StatsDatabase
//
//    init {
//        db = Room.databaseBuilder(app, StatsDatabase::class.java, "stats.db").build()
//    }
//
//    override suspend fun getStats(): Stats {
//        return db.statsDao().getStats()
//    }
//
//    override suspend fun addStats(){
//        db.statsDao().addStats(Stats(0 , 0,0,0,0,0,0,0,0,0,0,0))
//    }
//
//    override suspend fun updateStats(stat: Stats) {
//        db.statsDao().updateStat(stat)
//    }
//}