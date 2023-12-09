package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.IQuoteFetcher
import edu.towson.cosc435.kraft.sidequest.QuoteFetcher
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.IStatRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import edu.towson.cosc435.kraft.sidequest.data.model.LevelDatabaseRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.QuestDatabaseRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quote
import edu.towson.cosc435.kraft.sidequest.data.model.StatDatabaseRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Stats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.pow

class StatViewModel(app: Application): AndroidViewModel(app) {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val quest: State<List<Quest>> = _quests

    private val statRepos: IStatRepository = StatDatabaseRepository(getApplication())
    var stat: MutableState<Stats> = mutableStateOf(Stats(0, 0,0,0,0,0,0,0,0,0,0,0))

    private val _selected: MutableState<Quest?>
    val selectedQuest: State<Quest?>
    private val _repository: IQuestRepository = QuestDatabaseRepository(getApplication())

//    private val _level: MutableState<Level>
//    private val _levelSystem: ILevelSystem = LevelSystem()
    private val levelRepos: ILevelSystem = LevelDatabaseRepository(getApplication())
    var level: MutableState<Level> = mutableStateOf(Level(0,1, 0, 5))


    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>

    private val check: MutableState<Boolean>

    private val quoteFetcher : IQuoteFetcher = QuoteFetcher()
    val quotes : MutableState<Quote?> = mutableStateOf(null)
    var buttonClick : MutableState<Boolean> = mutableStateOf(false)
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

            if(levelRepos.getLevel() == null){
                levelRepos.addLevel(level.value)
                level.value = levelRepos.getLevel()
            } else {
                level.value = levelRepos.getLevel()
            }
        }
        check = mutableStateOf(true)
        _waiting = mutableStateOf(false)
        waiting = _waiting
        _selected = mutableStateOf(null)
        //_level = mutableStateOf(Level(1, 0, 5))
        //getLevelObj()
        selectedQuest = _selected
    }

    fun addQuest(quest: Quest) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _waiting.value = true
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
            newLevel(quest.exp)
            // updates level with new exp
            viewModelScope.launch {
                // update the statRepos value
                levelRepos.addExp(level.value)
                level.value = levelRepos.getLevel()
            }

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
    private fun newLevel(difficulty: DifficultyEnum) {
        val levelDouble: Double = level.value.level.toDouble()
        when(difficulty){
            DifficultyEnum.easy -> {
                level.value.currentExp += (3 * ((levelDouble).pow(0.7)) ).toLong()
                checkLevelUp()
            }

            DifficultyEnum.medium -> {
                level.value.currentExp += (5 * ((levelDouble).pow(0.7)) ).toLong()
                checkLevelUp()
            }

            DifficultyEnum.hard -> {
                level.value.currentExp += (9 * ((levelDouble).pow(0.7))).toLong()
                checkLevelUp()
            }

            else -> { // challenges go here
                level.value.currentExp = level.value.currentExp
                checkLevelUp()
            }
        }
    }
    private fun checkLevelUp() {
        if(level.value.currentExp >= level.value.expTillLevelUp){
            level.value.level += 1
            level.value.currentExp = level.value.currentExp - level.value.expTillLevelUp
            calculateExpForNextLevel()
            checkLevelUp()
        }
    }

    private fun calculateExpForNextLevel() {
        val levelDouble: Double = level.value.level.toDouble()
        level.value.expTillLevelUp = (2 * ((levelDouble + 1).pow(1.7)) - 2).toLong()
    }

    fun setCheck() {
        check.value = !check.value
    }
    fun getlevel(): Long {
        return level.value.level
    }
    fun getCurrentExp(): Long {
        return level.value.currentExp
    }

    fun getExpTillLevelUp(): Long{
        return level.value.expTillLevelUp
    }

    fun getQuoteAPI(){
        viewModelScope.launch {
            quotes.value = quoteFetcher.fetchQuotes()
        }
    }

    fun buttonClicked() {
        buttonClick.value = true
    }



}