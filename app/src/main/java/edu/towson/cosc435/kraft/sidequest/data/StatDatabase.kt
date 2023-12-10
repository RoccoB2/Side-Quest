package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Stats

// interface for level dao
@Dao
interface StatDao {
    @Query("select * from stats") // query function: sql string to select all data from the stats table
    suspend fun getStats(): Stats

    @Insert // query function: inserts a row into the stats table
    suspend fun addStats(stat: Stats)
    
    @Update // query function: updates a row in the stats table
    suspend fun updateStat(stat : Stats)

}

// instantiates the stats table with entity based on the stats data class
// implements the roomDatabase
@Database(entities = [Stats::class], version = 1, exportSchema = false)
abstract class StatsDatabase: RoomDatabase(){
    abstract fun statsDao(): StatDao // constructs an abstract function that implements the stats dao interface
}