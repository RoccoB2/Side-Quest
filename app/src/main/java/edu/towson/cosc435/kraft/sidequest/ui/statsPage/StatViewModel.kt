package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.impl.LevelSystem
import edu.towson.cosc435.kraft.sidequest.data.impl.QuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

class StatViewModel: ViewModel() {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val stats: State<List<Quest>> = _quests

    private val _selected: MutableState<Quest?>
    val selectedQuest: State<Quest?>
    private val _repository: IQuestRepository = QuestRepository()

    private val _level: MutableState<Level>
    private val _levelSystem: ILevelSystem = LevelSystem()
    init {
        _quests.value = _repository.getQuests()
        _selected = mutableStateOf(null)
        _level = mutableStateOf(Level(1, 0, 5))
        getLevelObj()
        selectedQuest = _selected
    }

    fun addQuest(quest: Quest) {
        _repository.addQuest(quest)
        _quests.value = _repository.getQuests()
        if(quest.status == StatusEnum.pass) {
            _levelSystem.addExp(quest.exp)
            getLevelObj()
        }
        else{
            // todo
        }
    }

    fun filter(search: String) {
        _quests.value = _repository.getQuests().filter { a -> a.description.contains(search, true)}
    }

    fun selectQuest(quest: Quest) {
        _selected.value = quest
    }
    fun getLevelObj(){
        _level.value = _levelSystem.getLevelObj()
    }

//    fun setLevel(){
//        _level.value.level = _levelSystem.getLevel()
//    }

//    fun addExp(difficulty: DifficultyEnum){
//        _levelSystem.addExp(difficulty)
//        setLevel()
//    }

    fun getLevel(): Long{
        return _level.value.level
    }

}