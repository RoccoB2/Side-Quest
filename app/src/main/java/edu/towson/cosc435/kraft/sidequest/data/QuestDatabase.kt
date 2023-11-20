package edu.towson.cosc435.kraft.sidequest.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

@Dao
interface QuestDao {
    @Query("select * from quests")
    suspend fun getQuests(): List<Quest>

    @Insert
    suspend fun addQuest(quest: Quest)

    @Delete
    suspend fun deleteQuest(quest: Quest)

    @Update
    suspend fun updateQuest(quest: Quest)
}

@Database(entities = [Quest::class], version = 1, exportSchema = false)
abstract class QuestsDatabase: RoomDatabase(){
    abstract fun questsDao(): QuestDao
}