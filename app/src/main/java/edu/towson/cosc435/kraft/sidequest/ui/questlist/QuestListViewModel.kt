package edu.towson.cosc435.kraft.sidequest.ui.questlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.impl.QuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

class QuestListViewModel: ViewModel() {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val quests: State<List<Quest>> = _quests

    private val _selected: MutableState<Quest?>
    val selectedQuest: State<Quest?>

    private val _repository: IQuestRepository = QuestRepository()

    init {
        _quests.value = _repository.getQuests()
        _selected = mutableStateOf(null)
        selectedQuest = _selected
    }

    fun addQuest(quest: Quest) {
        _repository.addQuest(quest)
        _quests.value = _repository.getQuests()
    }

    fun toggleStatus(quest: Quest) {
        _repository.toggleStatus(quest)
        _quests.value = _repository.getQuests()
    }

    fun filter(search: String) {
        _quests.value = _repository.getQuests().filter { a -> a.description.contains(search, true)}
    }

    fun selectQuest(quest: Quest) {
        _selected.value = quest
    }

}