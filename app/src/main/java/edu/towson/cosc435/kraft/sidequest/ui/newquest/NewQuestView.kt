package edu.towson.cosc435.kraft.sidequest.ui.newquest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalComposeUiApi
fun NewQuestView(
    vm: NewQuestViewModel = viewModel(),
    onAddQuest: (Quest) -> Unit
) {
        var validForm by remember { mutableStateOf(true) } //used to validate user input to verify required information is given
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ){
            Text( //title of the page
                "Create New Quest",
                fontSize = 25.sp,
                modifier = Modifier.padding(25.dp)
            )
            if(!validForm){ //displays if user tried to submit a new quest without all of the required information
                Text(
                    "Please fill out all required fields",
                    color = Color(0xFFDE5454), // red
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }
            OutlinedTextField( //user inputs information into the text field to set category for their new quest
                value = vm.category.value, //sets the value of the textfield to the category in the new quest view model
                onValueChange = vm::setCategory, //updates the category in the new quest viewmodel to match user input
                placeholder = {
                    Text( "Category") //placeholder (before user input this displays)
                },
                label = {
                    Text("Category (Required)")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(//user inputs information into the text field to set header for their new quest
                value = vm.header.value, //sets the value of the textfield to the header in the new quest view model
                onValueChange = vm::setHeader,//updates the header in the new quest viewmodel to match user input
                placeholder = {
                    Text( "Header")//placeholder (before user input this displays)
                },
                label = {
                    Text("Header (Required)")
                },
                singleLine = true,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(//user inputs information into the text field to set description for their new quest
                value = vm.description.value, //sets the value of the textfield to the description in the new quest view model
                onValueChange = vm::setDescription,//updates the description in the new quest viewmodel to match user input
                placeholder = {
                    Text( "Description")//placeholder (before user input this displays)
                },
                label = {
                    Text("Description")
                },
                modifier = Modifier.padding(10.dp)
            )

            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            var selectedDateText by remember { mutableStateOf("") }

            // Fetching current year, month and day
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            val datePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedDateText = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"//this string makes the standard american format of a date mm/dd/yyyy
                }, year, month, dayOfMonth
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 15.dp, bottom = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        datePicker.show() //displays the datepicker(calender) when the button is clicked
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(text = "Select a date")
                }
                Text(
                    text = if (selectedDateText.isNotEmpty()) {
                        vm.setDate(selectedDateText) //uses the new quest viewmodel function setDate to set the date to the selected date
                        "Date selected: $selectedDateText" //displays the selected date string
                    } else{
                        ""
                    }
                )
            }

            var selectedTimeText by remember { mutableStateOf("") }

            // Fetching current hour, and minute
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            var hourSaved by remember {mutableStateOf(0)}
            var minuteSaved by remember { mutableStateOf("") }
            var ampmSaved by remember { mutableStateOf("") }
            val timePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    hourSaved = if(selectedHour > 12){ //converts from military time by subtracting 12 from the hours if hour is above 12
                        selectedHour - 12
                    } else {
                        if(selectedHour == 0) //if time is midnight set to 12 instead of 00
                            12
                        else
                            selectedHour
                    }
                    minuteSaved = if(selectedMinute < 10){ //makes it so that single digit minutes have a 0 in front of them so 12:01 reads 12:01 and not 12:1
                        "0$selectedMinute"
                    } else {
                        "$selectedMinute"
                    }
                    ampmSaved = if(selectedHour >= 12){ //if the hour is above or equal to 12 then we know we should use PM else AM
                        "PM"
                    } else {
                        "AM"
                    }
                    selectedTimeText = "$hourSaved:$minuteSaved $ampmSaved" //this string allows us to have a time that is not in military time
                }, hour, minute, false
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 15.dp, bottom = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        timePicker.show()//displays the timepicker(clock) when the button is clicked
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(text = "Select time")
                }
                Text(
                    text = if (selectedTimeText.isNotEmpty()) {
                        vm.setTime(selectedTimeText)//uses the new quest viewmodel function setTime to set the date to the selected time
                        "Time selected: $selectedTimeText"//displays the selectedtime string
                    } else {
                        ""
                    }
                )
            }

            Text(
                "Quest Difficulty (Required)",
                fontSize = 15.sp,
                modifier = Modifier.padding(15.dp))
            Row {
                Button(
                    onClick = { vm.setExp(DifficultyEnum.easy) }, //if button clicked sets new quest difficulty to easy
                    colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.easy)
                            Color(0xFF54DE79) //changes color to green for easy
                        else
                            Color(0xFFA0A3A1)), //changes color to grey if not selected
                    modifier = Modifier.padding(10.dp)
                    )
                {
                    Text("Easy")
                }
                Button(
                    onClick = { vm.setExp(DifficultyEnum.medium) },//if button clicked sets new quest difficulty to medium
                    colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.medium)
                            Color(0xFFDED754) //changes color to yellow for medium
                        else
                            Color(0xFFA0A3A1)), //changes color to grey if not selected
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Medium")
                }
                Button(
                    onClick = { vm.setExp(DifficultyEnum.hard) },//if button clicked sets new quest difficulty to hard
                        colors = ButtonDefaults.buttonColors(
                        if (vm.getExp() == DifficultyEnum.hard)
                            Color(0xFFDE5454) //changes color to red for hard
                        else
                            Color(0xFFA0A3A1)), //changes color to grey if not selected
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Hard")
                }
            }
            Button(
                onClick = {
                    validForm = formValidate(vm)
                    if(validForm){ //checks if newquest is valid (has required information) before submit is allowed
                        val quest = vm.validate()
                        onAddQuest(quest) //adds quest to list
                    }
                    },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(top =10.dp, start =10.dp, end = 10.dp, bottom = 100.dp)
            ){
                Text("Submit")
            }
        }



}

fun formValidate(vm: NewQuestViewModel) : Boolean { //validation function to determine what information is required of the user
    return (vm.getCategory() != "" && vm.getHeader() != "" && vm.getExp() != DifficultyEnum.unassigned)
}