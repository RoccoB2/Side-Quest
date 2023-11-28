package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.data.IStatRepository
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
        db.statsDao().addStats(Stats(0 , 0,0,0,0,0,0,0,0,0,0,0))
    }

    override suspend fun updateStats(stat: Stats) {
        db.statsDao().updateStat(stat)
    }
}