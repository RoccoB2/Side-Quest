package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.data.IStatRepository
import edu.towson.cosc435.kraft.sidequest.data.StatsDatabase

// StatDatabaseRepository connects the frontend to the rooms database
// takes in an instance of the application to provide the context required by the rooms database api
// implements the IStatRepository interface to provide a framework for needed functions
class StatDatabaseRepository(app: Application): IStatRepository  {

    private val db: StatsDatabase // db will hold an instance of the quest database

    // init block executes the code below when initialized
    init {
        db = Room.databaseBuilder(app, StatsDatabase::class.java, "stats.db").build() // adds the database to the db variable
    }

    // retrieve the stats held in the stats table in the database
    override suspend fun getStats(): Stats {
        return db.statsDao().getStats() // returns stats
    }

    // adds a new stat to the database
    // because there is only one instance (row) of stats in the stat table of the database we can construct it here
    override suspend fun addStats(){
        // constructs and adds a stat object to the table
        db.statsDao().addStats(Stats(0 , 0,0,0,0,0,0,0,0,0,0,0))
    }

    // updates the only row in the stat table based on the stat passed in
    override suspend fun updateStats(stat: Stats) {
        db.statsDao().updateStat(stat) // updates stat table
    }
}