package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.data.model.Stats

// interface for the stat database repository
interface IStatRepository {

    // function used to get the stats of the user form the stat table of the database
    suspend fun getStats(): Stats

    // function used to add a row to the stats table in the database
    suspend fun addStats()

    // function used to update a row in the stats table of the database
    suspend fun updateStats(stat: Stats)

}
