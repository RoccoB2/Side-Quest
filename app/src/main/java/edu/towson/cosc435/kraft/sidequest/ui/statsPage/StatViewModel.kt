package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.IStatRepository
import edu.towson.cosc435.kraft.sidequest.data.impl.LevelSystem
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.QuestDatabaseRepository
import edu.towson.cosc435.kraft.sidequest.data.model.StatDatabaseRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Stats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatViewModel(app: Application): AndroidViewModel(app) {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val quest: State<List<Quest>> = _quests

    private val statRepos: IStatRepository = StatDatabaseRepository(getApplication())
    var stat: MutableState<Stats> = mutableStateOf(Stats(0, 0,0,0,0,0,0,0,0,0,0,0))
    private val _selected: MutableState<Quest?>
    val selectedQuest: State<Quest?>
    private val _repository: IQuestRepository = QuestDatabaseRepository(getApplication())

    private val _level: MutableState<Level>
    private val _levelSystem: ILevelSystem = LevelSystem()


    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>

    private val check: MutableState<Boolean>
    init {
        viewModelScope.launch{
            _quests.value = _repository.getQuests()
            _quests.value = _quests.value.filter{ q -> q.status != StatusEnum.pending }
            if(statRepos.getStats() == null){
                statRepos.addStats()
                stat.value = statRepos.getStats()
            } else {
                stat.value = statRepos.getStats()
            }

        }
        check = mutableStateOf(true)
        _waiting = mutableStateOf(false)
        waiting = _waiting
        _selected = mutableStateOf(null)
        _level = mutableStateOf(Level(1, 0, 5))
        getLevelObj()
        selectedQuest = _selected
    }

    fun addQuest(quest: Quest) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _waiting.value = true
//                delay(500)
                _quests.value = _repository.getQuests()
                _quests.value = _quests.value.filter { q -> q.status != StatusEnum.pending }
                _waiting.value = false
            }
        }
        stat.value.totalQuests += 1
        if(quest.status == StatusEnum.pass) {
            // updates passed stats
            stat.value.passedTotal += 1
            stat.value.currentStreak += 1
            if(stat.value.currentStreak > stat.value.longestStreak)
                stat.value.longestStreak = stat.value.currentStreak

            when(quest.exp){

                DifficultyEnum.easy -> stat.value.passedEasy += 1

                DifficultyEnum.medium -> stat.value.passedMedium += 1

                DifficultyEnum.hard -> stat.value.passedHard += 1

                else -> {}
            }

            // updates level with new exp
            _levelSystem.addExp(quest.exp)
            getLevelObj()
        } else{
            // updates failed stats
            stat.value.failedTotal += 1
            stat.value.currentStreak = 0

            when(quest.exp){
                DifficultyEnum.easy -> stat.value.failedEasy += 1

                DifficultyEnum.medium -> stat.value.failedMedium += 1

                DifficultyEnum.hard -> stat.value.failedHard += 1

                else -> {}
            }
        }

        viewModelScope.launch {
            // update the statRepos value
            statRepos.updateStats(stat.value)
            stat.value = statRepos.getStats()
        }
    }

    fun filter(search: String) {
        //_quests.value = _repository.getQuests().filter { a -> a.description.contains(search, true)}
    }

    fun selectQuest(quest: Quest) {
        _selected.value = quest
    }

    fun getLevelObj(){
        _level.value = _levelSystem.getLevelObj()
    }


    fun getLevel(): Long{
        return _level.value.level
    }

    fun getPassEasy(): Int {
        return stat.value.passedEasy//_passedEasy.value
    }

    fun getPassMedium(): Int {
        return stat.value.passedMedium//_passedMedium.value
    }

    fun getPassHard(): Int {
        return stat.value.passedHard//_passedHard.value
    }

    fun getPassTotal(): Int {
        return stat.value.passedTotal//_passedTotal.value
    }

    fun getFailEasy(): Int {
        return stat.value.failedEasy//_failedEasy.value
    }

    fun getFailMedium(): Int {
        return stat.value.failedMedium//_failedMedium.value
    }

    fun getFailHard(): Int {
        return stat.value.failedHard//_failedHard.value
    }

    fun getFailTotal(): Int {
        return stat.value.failedTotal//_failedTotal.value
    }

    fun getTotalQuests(): Int {
        return stat.value.totalQuests//_totalQuests.value
    }

    fun getLongestStreak(): Int {
        return stat.value.longestStreak//_longestStreak.value
    }

    fun getCurrentStreak(): Int {
        return stat.value.currentStreak//_currentStreak.value
    }

    fun getCheck(): Boolean {
        return check.value
    }

    fun setCheck() {
        check.value = !check.value
    }

}