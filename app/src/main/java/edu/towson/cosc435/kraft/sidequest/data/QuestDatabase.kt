package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

// interface for quest dao
@Dao
interface QuestDao {
    @Query("select * from quests") // query function: sql string to select all data from the quests table
    suspend fun getQuests(): List<Quest>

    @Insert // query function: inserts a row into the quests table
    suspend fun addQuest(quest: Quest)

    @Delete // query function: deletes a row from the quests table
    suspend fun deleteQuest(quest: Quest)

    @Update // query function: updates a row in the quests table
    suspend fun updateQuest(quest: Quest)
}

// instantiates the quests table with entity based on the quest data class
// implements the roomDatabase
@Database(entities = [Quest::class], version = 1, exportSchema = false)
abstract class QuestsDatabase: RoomDatabase(){
    abstract fun questsDao(): QuestDao // constructs an abstract function that implements the quest dao interface
}