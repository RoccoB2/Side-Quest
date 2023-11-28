package edu.towson.cosc435.kraft.sidequest.ui.questlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.QuestDatabaseRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import kotlinx.coroutines.launch

class QuestListViewModel(app : Application): AndroidViewModel(app) {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val quests: State<List<Quest>> = _quests

    private val _selected: MutableState<Quest?>
    val selectedQuest: State<Quest?>
    private val _currentIndex: MutableState<Int> = mutableStateOf(0)
    val currentIndex: State<Int> = _currentIndex

    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>


    private val _repository: IQuestRepository = QuestDatabaseRepository(getApplication())


    init {
        viewModelScope.launch{
            _quests.value = _repository.getQuests()
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending }
        }
        _selected = mutableStateOf(null)
        selectedQuest = _selected
        _waiting = mutableStateOf(false)
        waiting = _waiting
    }

    fun addQuest(quest: Quest) {
        viewModelScope.launch {
            _waiting.value = true
            _repository.addQuest(quest)
            _quests.value = _repository.getQuests()
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending }
            _waiting.value = false
        }
       // _repository.updateQuestList(quests.value)
    }

    fun deleteQuest(quest: Quest) {
//        viewModelScope.launch {
//            _waiting.value = true
//            _repository.deleteQuest(quest)
//            _quests.value = _repository.getQuests()
//            _waiting.value = false
//        }
        _quests.value = _quests.value.filter { q -> q.id != quest.id }
    }

    fun toggleStatus(quest: Quest,status: StatusEnum) {
        viewModelScope.launch {
            _repository.toggleStatus(quest, status)
            //_quests.value = _repository.getQuests()
        }
    }

    fun toggleDescription(quest: Quest) {

    }

    fun filter(search: String) {
        //_quests.value = _repository.getQuests().filter { a -> a.description.contains(search, true)}
    }

    fun selectQuest(quest: Quest?) {
        _selected.value = quest
    }

    fun isQuestSelected(): Boolean{
        return  _selected.value != null
    }

    fun getSelectedQuest(): Quest?{
        return _selected.value
    }

}