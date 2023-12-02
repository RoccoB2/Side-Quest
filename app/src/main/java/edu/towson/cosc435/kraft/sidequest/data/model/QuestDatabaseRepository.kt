package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.QuestsDatabase

class QuestDatabaseRepository(app: Application) : IQuestRepository {

    private val db: QuestsDatabase

    init {
        db = Room.databaseBuilder(app, QuestsDatabase::class.java, "quests.db").build()
    }
    override suspend fun getQuests(): List<Quest> {
        return db.questsDao().getQuests()
    }

    override suspend fun addQuest(quest: Quest) {
         db.questsDao().addQuest(quest)
    }

    override suspend fun toggleStatus(quest: Quest, status: StatusEnum) {
        db.questsDao().updateQuest(quest.copy(status = status))
    }

    override suspend fun deleteQuest(quest: Quest) {
         db.questsDao().deleteQuest(quest)
    }

//    override fun updateQuestList(quests: List<Quest>) {
//
//    }

}
