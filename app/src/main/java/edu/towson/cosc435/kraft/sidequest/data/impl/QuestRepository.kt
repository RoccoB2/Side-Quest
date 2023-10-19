package edu.towson.cosc435.kraft.sidequest.data.impl

import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import java.util.Date

class QuestRepository : IQuestRepository {
    private var _quests = listOf<Quest>()

    override fun getQuests(): List<Quest> {
        return _quests
    }
    override fun addQuest(quest: Quest) {
       _quests = listOf(quest) + _quests
    }

    override fun deleteQuest(quest: Quest) {
        _quests = _quests.filter { q -> q.id != quest.id}
    }

    override fun toggleStatus(quest: Quest) {
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
        } as List<Quest>
    }
}