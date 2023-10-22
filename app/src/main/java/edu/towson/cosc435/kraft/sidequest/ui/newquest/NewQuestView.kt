package edu.towson.cosc435.kraft.sidequest.ui.newquest

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalComposeUiApi
fun NewQuestView(
    vm: NewQuestViewModel = viewModel(),
    onAddQuest: (Quest) -> Unit
) {
        var validForm by remember { mutableStateOf(true) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                //state = rememberScrollState(),
                //orientation = Orientation.Vertical)
        ){
            Text(
                "Create New Quest",
                fontSize = 25.sp,
                modifier = Modifier.padding(25.dp)
            )
            if(!validForm){
                Text(
                    "Please fill out all required fields",
                    color = Color(0xFFDE5454), // red
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }
            OutlinedTextField(
                value = vm.category.value,
                onValueChange = vm::setCategory,
                placeholder = {
                    Text( "Category")
                },
                label = {
                    Text("Category (Required)")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = vm.header.value,
                onValueChange = vm::setHeader,
                placeholder = {
                    Text( "Header")
                },
                label = {
                    Text("Header (Required)")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = vm.description.value,
                onValueChange = vm::setDescription,
                placeholder = {
                    Text( "Description")
                },
                label = {
                    Text("Description")
                },
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = vm.date.value,
                onValueChange = vm::setDate,
                placeholder = {
                    Text( "Due date")
                },
                label = {
                    Text("Due date")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = vm.time.value,
                onValueChange = vm::setTime,
                placeholder = {
                    Text( "Time due")
                },
                label = {
                    Text("Time due")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                "Quest Difficulty (Required)",
                fontSize = 15.sp,
                modifier = Modifier.padding(15.dp))
            Row {
                Button(
                    onClick = { vm.setExp(DifficultyEnum.easy) },
                    colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.easy)
                            Color(0xFF54DE79) // green
                        else
                            Color(0xFFA0A3A1)), // grey
                    modifier = Modifier.padding(10.dp)
                    )
                {
                    Text("Easy")
                }
                Button(
                    onClick = { vm.setExp(DifficultyEnum.medium) },
                    colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.medium)
                            Color(0xFFDED754) // yellow
                        else
                            Color(0xFFA0A3A1)), // grey
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Medium")
                }
                Button(
                    onClick = { vm.setExp(DifficultyEnum.hard) },
                        colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.hard)
                            Color(0xFFDE5454) // red
                        else
                            Color(0xFFA0A3A1)), // grey
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Hard")
                }
            }
            Button(
                onClick = {
                    validForm = formValidate(vm)
                    if(validForm){
                        val quest = vm.validate()
                        onAddQuest(quest)
                    }
                    },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(top =10.dp, start =10.dp, end = 10.dp, bottom = 100.dp)
            ){
                Text("Submit")
            }
        }



}

fun formValidate(vm: NewQuestViewModel) : Boolean {
    return (vm.getCategory() != "" && vm.getHeader() != "" && vm.getExp() != DifficultyEnum.unassigned)
}