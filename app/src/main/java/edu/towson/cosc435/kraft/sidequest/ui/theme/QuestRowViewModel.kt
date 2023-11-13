package edu.towson.cosc435.kraft.sidequest.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class QuestRowViewModel: ViewModel() {

    val _openDialog = mutableStateOf(false)

    fun setOpenDialog(bool: Boolean){
        _openDialog.value = bool
    }

    fun getOpenDialog(): Boolean{
        return _openDialog.value
    }
}