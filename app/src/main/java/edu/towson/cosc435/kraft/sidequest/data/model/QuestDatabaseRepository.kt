package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.QuestsDatabase

// QuestDatabaseRepository connects the frontend to the rooms database
// takes in an instance of the application to provide the context required by the rooms database api
// implements the IQuestRepository interface to provide a framework for needed functions
class QuestDatabaseRepository(app: Application) : IQuestRepository {

    private val db: QuestsDatabase // db will hold an instance of the quest database

    // init block executes the code below when initialized
    init {
        db = Room.databaseBuilder(app, QuestsDatabase::class.java, "quests.db").build() // adds the database to the db variable
    }

    // retrieve the list of quests held in the quest table in the database
    override suspend fun getQuests(): List<Quest> {
        return db.questsDao().getQuests() // returns the list of quests
    }

    // adds a quest to the quest database table
    override suspend fun addQuest(quest: Quest) {
         db.questsDao().addQuest(quest) // adds quest
    }

    // updates the StatusEnum for a particular quest in the quest table of the database
    override suspend fun toggleStatus(quest: Quest, status: StatusEnum) {
        db.questsDao().updateQuest(quest.copy(status = status)) // calls update quest passing in a copy of the entire quest except the StatusEnum which is changed to the new value
    }

    // deletes a quest from the quest table
    override suspend fun deleteQuest(quest: Quest) {
         db.questsDao().deleteQuest(quest) // deletes the quest that was passed in
    }
}
