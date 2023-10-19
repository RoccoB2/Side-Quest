package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.data.model.Quest

interface IQuestRepository {
    fun getQuests(): List<Quest>
    fun deleteQuest(quest: Quest)
    fun addQuest(quest: Quest)
    fun toggleStatus(quest: Quest)


}