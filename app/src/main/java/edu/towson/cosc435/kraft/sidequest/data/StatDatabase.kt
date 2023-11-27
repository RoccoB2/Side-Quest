package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.Stats

@Dao
interface StatDao {
    @Query("select * from stats")
    suspend fun getStats(): Stats

    @Insert
    suspend fun addStats(stat: Stats)
    
    @Update
    suspend fun updateStat(stat : Stats)

}

@Database(entities = [Stats::class], version = 1, exportSchema = false)
abstract class StatsDatabase: RoomDatabase(){
    abstract fun statsDao(): StatDao
}