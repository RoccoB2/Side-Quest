package edu.towson.cosc435.kraft.sidequest.ui.questlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import edu.towson.cosc435.kraft.sidequest.data.IQuestRepository
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.data.model.QuestDatabaseRepository
import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.util.Log
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.towson.cosc435.kraft.sidequest.QuestCounterNotificationService
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import kotlinx.coroutines.launch
import java.util.Calendar

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

    val service = QuestCounterNotificationService(app)
    private var pendingCount: MutableState<Int> = mutableStateOf(0)
     val currentDate: MutableState<String> = mutableStateOf("")


    init {
        viewModelScope.launch{
            _quests.value = _repository.getQuests()
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending }
            setAlarms()
            pendingCount.value = _quests.value.size
            service.showNotification(pendingCount.value)
        }
        _selected = mutableStateOf(null)
        selectedQuest = _selected
        _waiting = mutableStateOf(false)
        waiting = _waiting
    }
    fun setAlarms(){
        val calendar = Calendar.getInstance()
        val context: Context = getApplication()
        currentDate.value = "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.YEAR)}"
        _quests.value.forEach{quest ->
            Log.d("look for me ", "if state was reached")
            if((quest.date==""|| quest.date == currentDate.value) && quest.time != ""){

                    val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                        putExtra(AlarmClock.EXTRA_MESSAGE, quest.header)
                        putExtra(AlarmClock.EXTRA_HOUR, timeConverterHour(quest.time))
                        putExtra(AlarmClock.EXTRA_MINUTES, timeConverterMinutes(quest.time))
                    }

                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(context,intent,null)
                    }
            }
        }
    }
    fun timeConverterHour(time: String):String{
        val hour : Int
        val isPm : Boolean = time.contains('P')
        val isDoubleDigitHour : Boolean = time.length == 8
        if(isPm){
            if (isDoubleDigitHour) {
                hour = "${time[0]}${time[1]}".toInt() + 12

            }else{
                hour = "${time[0]}".toInt() + 12
            }
        }else {
            if (isDoubleDigitHour) {
                hour = "${time[0]}${time[1]}".toInt()
            }else{
               return "0${time[0]}"
            }
        }
        if(hour == 24){
            return "00"
        }
        return hour.toString()
    }
    fun timeConverterMinutes(time: String):String{
        val isDoubleDigitHour : Boolean = time.length == 8
        if(isDoubleDigitHour){
            return "${time[3]}${time[4]}"
        } else {
            return "${time[4]}${time[5]}"
        }
    }
    fun addQuest(quest: Quest) {
        viewModelScope.launch {
            _waiting.value = true
            _repository.addQuest(quest)
            _quests.value = _repository.getQuests()
            _quests.value = _quests.value.filter{ q -> q.status == StatusEnum.pending }
            _waiting.value = false
            pendingCount.value = _quests.value.size
            service.showNotification(pendingCount.value)
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
        pendingCount.value = _quests.value.size
        service.showNotification(pendingCount.value)
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