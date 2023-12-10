package edu.towson.cosc435.kraft.sidequest.ui.newquest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

class NewQuestViewModel : ViewModel() {
    private val _category: MutableState<String> = mutableStateOf("")
    val category: State<String> = _category
    private val _description: MutableState<String> = mutableStateOf("")
    val description: State<String> = _description
    private val _date: MutableState<String> = mutableStateOf("")
    val date: State<String> = _date
    private val _time: MutableState<String> = mutableStateOf("")
    val time: State<String> = _time
    private val _exp: MutableState<DifficultyEnum> = mutableStateOf(DifficultyEnum.unassigned)
    val exp: State<DifficultyEnum> = _exp
    private val _status: MutableState<StatusEnum> = mutableStateOf(StatusEnum.pending)
    val status: State<StatusEnum> = _status
    private val _header: MutableState<String> = mutableStateOf("")
    val header: State<String> = _header
    private val _currentIndex: MutableState<Int> = mutableStateOf(0)
    val currentIndex: State<Int> = _currentIndex
    fun setCategory(category: String) { //function to set the category of new quest
        _category.value = category
    }

    fun getCategory() : String {//function to get the category of new quest
        return _category.value
    }

    fun setDescription(description: String) {//function to set the description of new quest
        _description.value = description
    }


    fun setDate(date: String) {//function to set the date of new quest
        _date.value = date
    }



    fun setTime(time: String) {//function to set the time of new quest
        _time.value = time
    }



    fun setExp(exp: DifficultyEnum) {//function to set the difficulty of new quest
        _exp.value = exp
    }

    fun getExp() : DifficultyEnum {//function to get the difficulty of new quest
        return _exp.value
    }



    fun setHeader(header: String) {//function to set the header of new quest
        _header.value = header
    }

    fun getHeader() : String {//function to get the header of new quest
        return _header.value
    }



    fun validate(): Quest { //used to validate and determine what fields are required for the user to input
        if(category.value.isEmpty()) { //checks if category was entered
            throw Exception("Category name needed")
        }
        if(header.value.isEmpty()) {//checks if header was entered
            throw Exception("Header needed")
        }
        if(exp.value == DifficultyEnum.unassigned) {//checks if difficulty was selected
            throw Exception("Difficulty needed")
        }
        return Quest(0, category.value, description.value, date.value, time.value, exp.value, status.value, header.value) //returns quest if it is valid
    }
}