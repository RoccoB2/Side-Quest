package edu.towson.cosc435.kraft.sidequest.data

import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

// interface for the quest database repository
interface IQuestRepository {

    // function to retrieve the list of quests from the quest table of the database
    suspend fun getQuests(): List<Quest>

    // function to add a quest to the quest table in the database
    suspend fun addQuest(quest: Quest)

    // function to update the statusEnum of a quest in the quest table of the database
    suspend fun toggleStatus(quest: Quest, status: StatusEnum)

    // function to delete a row from the quest table of the database
    suspend fun deleteQuest(quest: Quest)

}