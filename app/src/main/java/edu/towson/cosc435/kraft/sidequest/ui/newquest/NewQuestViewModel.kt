package edu.towson.cosc435.kraft.sidequest.ui.newquest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.impl.QuestRepository
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
    fun setCategory(category: String) {
        _category.value = category
    }

    fun getCategory() : String {
        return _category.value
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun getDescription() : String {
        return _description.value
    }

    fun setDate(date: String) {
        _date.value = date
    }

    fun getDate() : String {
        return _date.value
    }

    fun setTime(time: String) {
        _time.value = time
    }

    fun getTime() : String {
        return _time.value
    }

    fun setExp(exp: DifficultyEnum) {
        _exp.value = exp
    }

    fun getExp() : DifficultyEnum {
        return _exp.value
    }

    fun setStatus(status: StatusEnum) {
        _status.value = status
    }

    fun getStatus(status: StatusEnum) : StatusEnum {
        return _status.value
    }

    fun setHeader(header: String) {
        _header.value = header
    }

    fun getHeader() : String {
        return _header.value
    }



    fun validate(): Quest {
        if(category.value.isEmpty()) {
            throw Exception("Category name needed")
        }
        if(header.value.isEmpty()) {
            throw Exception("Header needed")
        }
        if(exp.value == DifficultyEnum.unassigned) {
            throw Exception("Difficulty needed")
        }
        return Quest(0, category.value, description.value, date.value, time.value, exp.value, status.value, header.value)
    }
}