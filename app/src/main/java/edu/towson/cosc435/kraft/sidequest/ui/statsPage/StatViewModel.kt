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

    private val _passedEasy: MutableState<Int>
    private val _passedMedium: MutableState<Int>
    private val _passedHard: MutableState<Int>
    private val _passedTotal: MutableState<Int>

    private val _failedEasy: MutableState<Int>
    private val _failedMedium: MutableState<Int>
    private val _failedHard: MutableState<Int>
    private val _failedTotal: MutableState<Int>

    private val _totalQuests: MutableState<Int>

    private val _longestStreak: MutableState<Int>
    private val _currentStreak: MutableState<Int>

    init {
        _quests.value = _repository.getQuests()
        _selected = mutableStateOf(null)
        _level = mutableStateOf(Level(1, 0, 5))
        getLevelObj()
        selectedQuest = _selected
        _passedEasy = mutableStateOf(0)
        _passedMedium = mutableStateOf(0)
        _passedHard = mutableStateOf(0)
        _passedTotal = mutableStateOf(0)
        _failedEasy = mutableStateOf(0)
        _failedMedium = mutableStateOf(0)
        _failedHard = mutableStateOf(0)
        _failedTotal = mutableStateOf(0)
        _totalQuests = mutableStateOf(0)
        _longestStreak = mutableStateOf(0)
        _currentStreak = mutableStateOf(0)
    }

    fun addQuest(quest: Quest) {
        _repository.addQuest(quest)
        _quests.value = _repository.getQuests()
        _totalQuests.value += 1
        if(quest.status == StatusEnum.pass) {
            // updates passed stats
            _passedTotal.value += 1
            _currentStreak.value += 1
            checkLongestStreak()
            when(quest.exp){
                DifficultyEnum.easy -> _passedEasy.value += 1

                DifficultyEnum.medium -> _passedMedium.value += 1

                DifficultyEnum.hard -> _passedHard.value += 1
                
                else -> {}
            }
            // updates level with new exp
            _levelSystem.addExp(quest.exp)
            getLevelObj()
        }
        else{
            // updates failed stats
            _failedTotal.value += 1
            _currentStreak.value = 0
            when(quest.exp){
                DifficultyEnum.easy -> _failedEasy.value += 1

                DifficultyEnum.medium -> _failedMedium.value += 1

                DifficultyEnum.hard -> _failedHard.value += 1

                else -> {}
            }
            // todo - of updating level on fail?
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

    fun checkLongestStreak(){
        if(_currentStreak.value > _longestStreak.value)
            _longestStreak.value = _currentStreak.value
    }

    fun getLevel(): Long{
        return _level.value.level
    }
    
    fun getPassEasy(): Int {
        return _passedEasy.value
    }

    fun getPassMedium(): Int {
        return _passedMedium.value
    }

    fun getPassHard(): Int {
        return _passedHard.value
    }

    fun getPassTotal(): Int {
        return _passedTotal.value
    }

    fun getFailEasy(): Int {
        return _failedEasy.value
    }

    fun getFailMedium(): Int {
        return _failedMedium.value
    }

    fun getFailHard(): Int {
        return _failedHard.value
    }

    fun getFailTotal(): Int {
        return _failedTotal.value
    }

    fun getTotalQuests(): Int {
        return _totalQuests.value
    }

    fun getLongestStreak(): Int {
        return _longestStreak.value
    }

    fun getCurrentStreak(): Int {
        return _currentStreak.value
    }

}