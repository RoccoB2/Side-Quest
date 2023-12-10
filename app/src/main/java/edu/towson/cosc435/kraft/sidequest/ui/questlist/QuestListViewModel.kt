package edu.towson.cosc435.kraft.sidequest.ui.questlist

import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.QuestCounterNotificationService
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.QuestDatabaseRepository
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.pow

class QuestListViewModel(app : Application): AndroidViewModel(app) {
    private val _quests: MutableState<List<Quest>> = mutableStateOf(listOf()) // holds the current list of pending quests
    val quests: State<List<Quest>> = _quests

    private val _selected: MutableState<Quest?> // holds the quest that has been selected in the quest list for the dialog box to display all quest details
    private val selectedQuest: State<Quest?>

    private val _waiting: MutableState<Boolean> // boolean variable to see if coroutine is still executing
    private val waiting: State<Boolean>

    private val _repository: IQuestRepository = QuestDatabaseRepository(getApplication()) // instance of the quest database

    private val service = QuestCounterNotificationService(app) // instance of the QuestCounterNotificationService for displaying notifications from adding a quest
    private var pendingCount: MutableState<Int> = mutableStateOf(0) // integer that holds the current total of pending quests in the database

    private val currentDate: MutableState<String> = mutableStateOf("") // string to hold the current date for checking if an alarm should be displayed


    init {
        // coroutine for pulling quests from database
        viewModelScope.launch{
            _quests.value = _repository.getQuests() // pulls all quests
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending } // filters out all non-pending quests
            pendingCount.value = _quests.value.size // updates count of total pending quests
            service.showNotification(pendingCount.value) // creates notification of total pending quests
        }
        _selected = mutableStateOf(null) // initializes selected quest to null
        selectedQuest = _selected
        _waiting = mutableStateOf(false) // initializes waiting to false
        waiting = _waiting
    }

    // function to set alarm for added quest with a time constraint
    private fun setAlarm(quest: Quest) {
        val calendar = Calendar.getInstance() // gets instance of calender with today's date
        val context: Context = getApplication() // gets context for intent
        currentDate.value = "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.YEAR)}" // formats calendars date to the date we use for quests

        // checks if the quest is due today or does not have a date due
        // all newly added quests with no due date AND A time due or considered due today
        // checks if there is a time due
        if((quest.date==""|| quest.date == currentDate.value) && quest.time != ""){

            // create intent for clock app with an hour and minute to go off and a message based on the quest
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, quest.header) // message set to the quests header
                putExtra(AlarmClock.EXTRA_HOUR, timeConverterHour(quest.time)) // hour set to the hour due
                putExtra(AlarmClock.EXTRA_MINUTES, timeConverterMinutes(quest.time)) // minute set to the minute due
                putExtra(AlarmClock.EXTRA_SKIP_UI, true) // does not auto open the clock app
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // adds new task flag to intent

            // checks if there is an app that can receive our intent
            if (intent.resolveActivity(context.packageManager) != null) {
                startActivity(context, intent, null) // sends intent to clock app
            }
        }
    }

    // helper function to parse the string to get the hour in the format required for the clock app intent
    private fun timeConverterHour(time: String): Int {
        val hour : Int // saves hour
        val isPm : Boolean = time.contains('P') // checks if its an am or pm time
        val isDoubleDigitHour : Boolean = time.length == 8 // checks if the hour due is in the double digits

        if(isPm){ // for hours due in pm

            hour = if (isDoubleDigitHour) { // get double digit hour
                "${time[0]}${time[1]}".toInt() + 12

            }else{ // get single digit hour
                "${time[0]}".toInt() + 12
            }

        }else { // for hours due in am

            hour = if (isDoubleDigitHour) { // get double digit hour
                "${time[0]}${time[1]}".toInt()

            }else{ // get single digit hour
                "${time[0]}".toInt()
            }

        }

        if(hour == 24){ // if the hour is due at 12 am (conversion for military time)
            return 0
        }

        return hour // return the hour due
    }

    // helper function to parse the string to get the minute in the format required for the clock app intent
    private fun timeConverterMinutes(time: String): Int {
        val isDoubleDigitHour : Boolean = time.length == 8 // checks if the hour due is in the double digits

        return if(isDoubleDigitHour){ // if the hour is double digit
            "${time[3]}${time[4]}".toInt() // return the minute

        } else { // if the hour is single digit
            "${time[2]}${time[3]}".toInt() // return the minute
        }
    }

    // function to add quest to the list of quests and save it to database
    fun addQuest(quest: Quest) {
        viewModelScope.launch { // coroutine for saving data to database
            _waiting.value = true // sets waiting value to true
            _repository.addQuest(quest) // adds quest to database
            _quests.value = _repository.getQuests() // updates the list of quests to all quests in database
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending } // filters out all non-pending quests
            _waiting.value = false // sets waiting to false
            pendingCount.value = _quests.value.size // updates count of pending quests
            service.showNotification(pendingCount.value) // sends new notification of pending quests
            setAlarm(quest) // calls set alarm for the quest (sets alarm depending on if the quest meets certain criteria
        }
    }

    // function to remove quests from quest list that have been completed
    fun deleteQuest(quest: Quest) {
        _quests.value = _quests.value.filter { q -> q.id != quest.id } // filters out all no pending quests
        pendingCount.value = _quests.value.size // updates count of pending quests
        service.showNotification(pendingCount.value) // sends new notification of pending quests
    }

    // function to update a quest when it has been passed or failed
    fun toggleStatus(quest: Quest,status: StatusEnum) {
        viewModelScope.launch { // launches coroutine to update quest in the database
            _repository.toggleStatus(quest, status) // calls function to update quest in the database
        }
    }

    // function to hold quest that has been selected
    fun selectQuest(quest: Quest?) {
        _selected.value = quest // sets selected value to the quest that has been selected
    }

    // function used to check if a quest has been selected
    fun isQuestSelected(): Boolean{
        return  _selected.value != null // returns true if a quest has been selected else returns false
    }

    // function to get the quest that has been selected
    fun getSelectedQuest(): Quest?{
        return _selected.value // returns selected quest
    }

    fun calculateExp(difficulty: DifficultyEnum, level: Long): Long {
        val levelDouble: Double = level.toDouble()
        return when(difficulty){
            DifficultyEnum.easy -> {
                (3 * ((levelDouble).pow(0.7)) ).toLong()
            }

            DifficultyEnum.medium -> {
                (5 * ((levelDouble).pow(0.7)) ).toLong()
            }

            DifficultyEnum.hard -> {
                (9 * ((levelDouble).pow(0.7))).toLong()
            }

            else -> {
                0
            }
        }
    }

}