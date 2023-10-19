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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical)
        ){
            Text(
                "Create New Quest",
                fontSize = 25.sp,
                modifier = Modifier.padding(25.dp)
            )
            OutlinedTextField(
                value = vm.category.value,
                onValueChange = vm::setCategory,
                placeholder = {
                    Text( "Category")
                },
                label = {
                    Text("Category")
                },
                singleLine = true,
                modifier = Modifier.padding(20.dp)
            )
            OutlinedTextField(
                value = vm.header.value,
                onValueChange = vm::setHeader,
                placeholder = {
                    Text( "Header")
                },
                label = {
                    Text("Header")
                },
                singleLine = true,
                modifier = Modifier.padding(20.dp)
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
                modifier = Modifier.padding(20.dp)
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
                modifier = Modifier.padding(20.dp)
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
                modifier = Modifier.padding(20.dp)
            )
            Row {
                Button(onClick = { vm.setExp(DifficultyEnum.easy) }) {
                    "test"
                }
                Button(onClick = { vm.setExp(DifficultyEnum.medium) }) {

                }
                Button(onClick = { vm.setExp(DifficultyEnum.hard) }) {

                }
            }
        }



}