package edu.towson.cosc435.kraft.sidequest.data.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import java.util.Date

class QuestRepository : IQuestRepository {
    private var _quests = listOf<Quest>()
    //private var _currentIndex: Int = 0
    override suspend fun getQuests(): List<Quest> {
        return _quests
    }
    override suspend fun addQuest(quest: Quest) {
        val newQuest: Quest = Quest(0, quest.category, quest.description, quest.date, quest.time, quest.exp, quest.status, quest.header)
        //_currentIndex +=1
       _quests = listOf(newQuest) + _quests
    }


    override suspend fun deleteQuest(quest: Quest) {
        _quests = _quests.filter { q -> q.id != quest.id }
    }
    override suspend fun toggleStatus(quest: Quest, status: StatusEnum) {
        _quests = _quests.map { q ->
            if (q.id == quest.id) {
                if (quest.status == StatusEnum.pass) {
                    q.copy(status = StatusEnum.fail)
                } else if (quest.status == StatusEnum.fail) {
                    q.copy(status = StatusEnum.pending)
                } else if (quest.status == StatusEnum.pending) {
                    q.copy(status = StatusEnum.pass)
                }
                else
                    q
            } else {
                q
            }
        } //as List<Quest>
    }

//    override fun updateQuestList(quests: List<Quest>){
//        _quests = quests
//    }

}