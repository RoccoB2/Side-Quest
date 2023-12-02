package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

interface IQuestRepository {
    suspend fun getQuests(): List<Quest>
    suspend fun addQuest(quest: Quest)
    suspend fun toggleStatus(quest: Quest, status: StatusEnum)
    suspend fun deleteQuest(quest: Quest)

    //fun updateQuestList(quests: List<Quest>)

}