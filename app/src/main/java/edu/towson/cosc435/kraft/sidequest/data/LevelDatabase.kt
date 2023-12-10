package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Level

// interface for level dao
@Dao
interface LevelDao {
    @Query("select * from levels") // query function: sql string to select all data from the levels table
    suspend fun getLevel(): Level

    @Insert // query function: inserts a row into the level table
    suspend fun addLevel(level: Level)

    @Update // query function: updates a row in the level table
    suspend fun updateLevel(level: Level)

}

// instantiates the level table with entity based on the level data class
// implements the roomDatabase
@Database(entities = [Level::class], version = 1, exportSchema = false)
abstract class LevelDatabase: RoomDatabase(){
    abstract fun levelDao(): LevelDao // constructs an abstract function that implements the level dao interface
}