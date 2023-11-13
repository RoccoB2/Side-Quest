package edu.towson.cosc435.kraft.sidequest.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import kotlinx.coroutines.launch


@Composable
fun QuestRow(
    quest: Quest,
    onPassQuest: (Quest) -> Unit,
    onDeleteQuest: (Quest) -> Unit,
    vm: QuestRowViewModel
) {
//    val vm: QuestRowViewModel = viewModel()
    if(vm.getOpenDialog())
        cardDescription(quest = quest, vm)
    Card(
        modifier = Modifier
            .padding(20.dp)
            .clickable(onClick = {
                vm.setOpenDialog(true)
            })
            .fillMaxWidth()
            .height(140.dp)
    ){
//        if(clicked) {
//            cardDescription(quest = quest, onDismissRequest = {})
//        }
        Text(quest.header, fontSize = 25.sp, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth() )
        Row(
            modifier = Modifier.padding(start = 5.dp, end =5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column() {
//                Row(
//                    modifier = Modifier.padding(5.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(quest.header, fontSize = 25.sp)
//                }
                Row(
                    modifier = Modifier.padding(top = 0.dp, start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Difficulty: ${getDifficulty(quest.exp)}", fontSize = 15.sp)

                }
                if (quest.date.isNotEmpty())
                    Text(text ="Date: ${quest.date}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))
                if (quest.time.isNotEmpty())
                    Text(text ="Time: ${quest.time}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))

            }
            Column() {
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Button(
                        onClick = {
                            val newQuest: Quest = Quest(quest.id, quest.category,quest.description,quest.date, quest.time,quest.exp,StatusEnum.pass, quest.header)
                            onPassQuest(newQuest)
                            onDeleteQuest(quest)
                                  },
                        colors = ButtonDefaults.buttonColors(Color(0xFF54DE79)), //green
                        modifier = Modifier
                            .padding(start = 15.dp, end = 5.dp)
                            .width(90.dp)
                            .height(45.dp)
                    )
                    {
                        Text("Pass")
                    }
                    Button(
                        onClick = {
                            val newQuest: Quest = Quest(quest.id, quest.category,quest.description,quest.date, quest.time,quest.exp,StatusEnum.fail, quest.header)
                            onPassQuest(newQuest)
                            onDeleteQuest(quest)
                                  },
                        colors = ButtonDefaults.buttonColors(Color(0xFFDE5454)), //red
                        modifier = Modifier
                            .padding(start = 10.dp, end = 5.dp)
                            .width(90.dp)
                            .height(45.dp)
                    )
                    {
                        Text("Fail")
                    }
                }

            }
        }
    }
}

fun getDifficulty(difficulty: DifficultyEnum): String {
    return when(difficulty){
        DifficultyEnum.easy ->  "Easy"
        DifficultyEnum.medium -> "Medium"
        DifficultyEnum.hard ->  "Hard"
        else -> {""}
    }

}

@Composable
fun cardDescription(quest: Quest, vm: QuestRowViewModel) {
    val check: Boolean = true
    Dialog(onDismissRequest = {
        vm.getOpenDialog()
        vm.setOpenDialog(!vm.getOpenDialog())
    }) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(140.dp)
        ){

            Text(quest.header, fontSize = 25.sp, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth() )
            Row(
                modifier = Modifier.padding(start = 5.dp, end =5.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column() {
//                Row(
//                    modifier = Modifier.padding(5.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(quest.header, fontSize = 25.sp)
//                }
                    Row(
                        modifier = Modifier.padding(top = 0.dp, start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Difficulty: ${getDifficulty(quest.exp)}", fontSize = 15.sp)

                    }
                    if (quest.date.isNotEmpty())
                        Text(text ="Date: ${quest.date}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))
                    if (quest.time.isNotEmpty())
                        Text(text ="Time: ${quest.time}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))

                }
            }
        }
    }
}
