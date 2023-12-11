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
    //list of quests
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf())
    val quest: State<List<Quest>> = _quests

    private val statRepos: IStatRepository = StatDatabaseRepository(getApplication())
    private var stat: MutableState<Stats> = mutableStateOf(Stats(0, 0,0,0,0,0,0,0,0,0,0,0))//initializes all stats to 0
    //selected quest variables
    private val _selected: MutableState<Quest?>
    private val selectedQuest: State<Quest?>
    //database for quests
    private val _repository: IQuestRepository = QuestDatabaseRepository(getApplication())
    //database for stats
    private val levelRepos: ILevelSystem = LevelDatabaseRepository(getApplication())
    //initializes level
    var level: MutableState<Level> = mutableStateOf(Level(0,1, 0, 5))


    private val _waiting: MutableState<Boolean>
    private val waiting: State<Boolean>

    private val check: MutableState<Boolean>
    //quote fetcher to pull inspirational quote
    private val quoteFetcher : IQuoteFetcher = QuoteFetcher()
    val quotes : MutableState<Quote?> = mutableStateOf(null)
    //button click disables quote button when true
    var buttonClick : MutableState<Boolean> = mutableStateOf(false)
    init {
        viewModelScope.launch{
            //initializes _quests to the database
            _quests.value = _repository.getQuests()
            //filters _quests into only quests that are pending
            _quests.value = _quests.value.filter{ q -> q.status != StatusEnum.pending }
            //initializes stats from database
            if(statRepos.getStats() == null){
                statRepos.addStats()
                stat.value = statRepos.getStats()
            } else {
                stat.value = statRepos.getStats()
            }
            //initializes level from database
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
        selectedQuest = _selected
    }
    //adds quest to the stat list
    fun addQuest(quest: Quest) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _waiting.value = true
                //sets quests to the quest database
                _quests.value = _repository.getQuests()
                //filters quest list to only pending quest
                _quests.value = _quests.value.filter { q -> q.status != StatusEnum.pending }
                _waiting.value = false
            }
        }
        stat.value.totalQuests += 1//increases total quests by 1
        if(quest.status == StatusEnum.pass) {//checks if quest was passed
            // updates passed stats
            stat.value.passedTotal += 1 //increases total passed quests by 1
            stat.value.currentStreak += 1 //increaes current streak by 1
            if(stat.value.currentStreak > stat.value.longestStreak)
                stat.value.longestStreak = stat.value.currentStreak

            when(quest.exp){

                DifficultyEnum.easy -> stat.value.passedEasy += 1//increaes passed easy by 1

                DifficultyEnum.medium -> stat.value.passedMedium += 1//increases passed medium by 1

                DifficultyEnum.hard -> stat.value.passedHard += 1//increased passed hard by 1

                else -> {}
            }
            newLevel(quest.exp)//calls newLevel
            // updates level with new exp
            viewModelScope.launch {
                // update the statRepos value
                levelRepos.addExp(level.value)
                level.value = levelRepos.getLevel()
            }

        } else{
            // updates failed stats
            stat.value.failedTotal += 1//increaes failed total by 1
            stat.value.currentStreak = 0 //resets current streak to 0 when quest is failed

            when(quest.exp){
                DifficultyEnum.easy -> stat.value.failedEasy += 1//increases failed easy by 1

                DifficultyEnum.medium -> stat.value.failedMedium += 1//increases failed medium by 1

                DifficultyEnum.hard -> stat.value.failedHard += 1//increased failed hard by 1

                else -> {}
            }
        }
        viewModelScope.launch {
            // update the statRepos value
            statRepos.updateStats(stat.value)//updates stat database
            stat.value = statRepos.getStats()//sets stats to database
        }
    }




    fun getPassEasy(): Int {
        return stat.value.passedEasy//returns number of easy quests passed
    }

    fun getPassMedium(): Int {
        return stat.value.passedMedium//returns number of medium quests passed
    }

    fun getPassHard(): Int {
        return stat.value.passedHard//returns number of hard quests passed
    }

    fun getPassTotal(): Int {
        return stat.value.passedTotal//returns total number of passed quests
    }

    fun getFailEasy(): Int {
        return stat.value.failedEasy//returns number of easy quests failed
    }

    fun getFailMedium(): Int {
        return stat.value.failedMedium//returns number of medium quests failed
    }

    fun getFailHard(): Int {
        return stat.value.failedHard//returns number of hard quests failed
    }

    fun getFailTotal(): Int {
        return stat.value.failedTotal//returns total number of failed quests
    }

    fun getTotalQuests(): Int {
        return stat.value.totalQuests//returns total number of completed quests
    }

    fun getLongestStreak(): Int {
        return stat.value.longestStreak//returns longest streak
    }

    fun getCurrentStreak(): Int {
        return stat.value.currentStreak//returns current streak
    }


    private fun newLevel(difficulty: DifficultyEnum) {
        val levelDouble: Double = level.value.level.toDouble()
        when(difficulty){
            DifficultyEnum.easy -> {
                level.value.currentExp += (3 * ((levelDouble).pow(0.9)) ).toLong() //function for easy exp added to current when easy quest is completed
                checkLevelUp()//checks if level up should occur
            }

            DifficultyEnum.medium -> {
                level.value.currentExp += (5 * ((levelDouble).pow(0.9)) ).toLong()//function for medium exp added to current when medium quest is completed
                checkLevelUp()//checks if level up should occur
            }

            DifficultyEnum.hard -> {
                level.value.currentExp += (8.5 * ((levelDouble).pow(0.9))).toLong()//function for hard exp added to current when hard quest is completed
                checkLevelUp()//checks if level up should occur
            }

            else -> { // challenges go here
                level.value.currentExp = level.value.currentExp
                checkLevelUp()//checks if level up should occur
            }
        }
    }
    private fun checkLevelUp() {//checks if level up should occur
        if(level.value.currentExp >= level.value.expTillLevelUp){//compares current exp and exp till level up
            level.value.level += 1//increases level by 1
            level.value.currentExp = level.value.currentExp - level.value.expTillLevelUp//calculates new current exp
            calculateExpForNextLevel()//gets new exp till level up
            checkLevelUp()//double checks for another level up
        }
    }

    private fun calculateExpForNextLevel() {//calculates exp for next level up
        val levelDouble: Double = level.value.level.toDouble()
        level.value.expTillLevelUp = (2 * ((levelDouble + 1).pow(1.7)) - 2).toLong()//function for exp of next level(exp till level up)
    }


    fun getlevel(): Long {
        return level.value.level //returns level
    }
    fun getCurrentExp(): Long {
        return level.value.currentExp //returns currentexp
    }

    fun getExpTillLevelUp(): Long{
        return level.value.expTillLevelUp//returns exptilllevelup
    }

    fun getQuoteAPI(){
        viewModelScope.launch {
            quotes.value = quoteFetcher.fetchQuotes()//calls quotefetcher to get quote
        }
    }

    fun buttonClicked() {
        buttonClick.value = true//sets button click to true so the get inspirational quote button locks
    }



}