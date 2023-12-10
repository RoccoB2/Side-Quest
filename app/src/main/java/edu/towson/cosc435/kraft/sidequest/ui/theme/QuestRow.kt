package edu.towson.cosc435.kraft.sidequest.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import kotlin.reflect.KSuspendFunction1


@Composable
fun QuestRow(
    quest: Quest,
    onToggle: (Quest, StatusEnum) -> Unit,
    onPassQuest: (Quest) -> Unit,
    onDeleteQuest: (Quest) -> Unit,
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    getSelectedQuest: () -> Quest?,
    calculateExp: (DifficultyEnum, Long) -> Long,
    getLevel: () -> Long
) {
    if(isQuestSelected())
        CardDescription(selectQuest, isQuestSelected, getSelectedQuest(), calculateExp, getLevel)
    Card(
        modifier = Modifier
            .padding(20.dp)
            .clickable(onClick = {
                selectQuest(quest)
            })
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
    ){
        Text(quest.header, fontSize = 25.sp, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth() )
        Row(
            modifier = Modifier.padding(start = 5.dp, end =5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column() {

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
                Text(text = "+${calculateExp(quest.exp, getLevel())} Exp", modifier = Modifier.padding(top = 5.dp, start = 5.dp), color = Color(0xFFFF7700))

            }
            Column() {
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Button(
                        onClick = {
                            val newQuest: Quest = Quest(quest.id, quest.category,quest.description,quest.date, quest.time,quest.exp,StatusEnum.pass, quest.header)
                            onToggle(quest, newQuest.status)
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
                            onToggle(quest, newQuest.status)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardDescription(
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    quest: Quest?,
    calculateExp: (DifficultyEnum, Long) -> Long,
    getLevel: () -> Long
) {
    Dialog(onDismissRequest = {
        selectQuest(null)
        isQuestSelected()
    }
    ) {
//       (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.5f)
//        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
//        dialogWindowProvider?.window?.setDimAmount(0.5f)
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(170.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
            ) {

                Row(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FlowColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .weight(1f),
//                        horizontalAlignment = Alignment.Start,

                        ) {
                        Text(
                            quest!!.header,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Difficulty: ${getDifficulty(quest.exp)}", fontSize = 15.sp)

                        if (quest.date.isNotEmpty()) {
                            Text(
                                text = "Date: ${quest.date}",
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                        if (quest.time.isNotEmpty()) {
                            Text(
                                text = "Time: ${quest.time}",
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                        Text(
                            text = "+${calculateExp(quest.exp, getLevel())} Exp",
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                            color = Color(0xFFFF7700)
                        )

                        if (quest.description.isNotEmpty()) {
                            Text(
                                text = "Description: ${quest.description}",
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                }
            }
        }
    }
}
