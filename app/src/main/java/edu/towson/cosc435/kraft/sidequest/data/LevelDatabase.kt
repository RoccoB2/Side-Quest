package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.Stats

@Dao
interface LevelDao {
    @Query("select * from levels")
    suspend fun getLevel(): Level

    @Insert
    suspend fun addLevel(level: Level)

    @Update
    suspend fun updateLevel(level: Level)

}

@Database(entities = [Level::class], version = 1, exportSchema = false)
abstract class LevelDatabase: RoomDatabase(){
    abstract fun levelDao(): LevelDao
}