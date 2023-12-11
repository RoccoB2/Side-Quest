package edu.towson.cosc435.kraft.sidequest.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest


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
    getLevel: () -> Long,
    getCurrentExp: () -> Long,
    getExpTillLevelUp: () -> Long,
) {
    if(isQuestSelected())
        CardDescription(selectQuest, isQuestSelected, getSelectedQuest(), calculateExp, getLevel, getCurrentExp, getExpTillLevelUp)//calls function for dialog if a quest in the list is clicked
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
                Text(" Category: ${quest.category}")
                Row(
                    modifier = Modifier.padding(top = 0.dp, start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Difficulty: ${getDifficulty(quest.exp)}", fontSize = 15.sp)//displays difficulty of the quest
                }
                if (quest.date.isNotEmpty())
                    Text(text ="Date: ${quest.date}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))//displays date of the quest
                if (quest.time.isNotEmpty())
                    Text(text ="Time: ${quest.time}",modifier = Modifier.padding(top = 5.dp, start = 5.dp))//displays time of the quest
                if(calculateExp(quest.exp, getLevel()) + getCurrentExp() >= getExpTillLevelUp()){
                    Text(text = "+${calculateExp(quest.exp, getLevel())} Exp (LEVEL UP)", modifier = Modifier.padding(top = 5.dp, start = 5.dp), color = Color(0xFFFF7700))//displays amount of exp given on quest completion
                }
                else{
                    Text(text = "+${calculateExp(quest.exp, getLevel())} Exp", modifier = Modifier.padding(top = 5.dp, start = 5.dp), color = Color(0xFFFF7700))//displays amount of exp given on quest completion
                }

            }
            Column() {
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Button( //button to pass quest displayed in green
                        onClick = {//onclick creates a new quest set to the old one, passes the newquest onto the stats screen and deletes the old quest of the quest list
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
                        Text(
                            text = "Pass",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Button(//button to quest displayed in red
                        onClick = {//onclick creates a new quest set to the old one, passes the newquest onto the stats screen and deletes the old quest of the quest list
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
                        Text(
                            text = "Fail",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

            }
        }
    }
}

fun getDifficulty(difficulty: DifficultyEnum): String {//returns a string depending on the difficulty passed into this function
    return when(difficulty){
        DifficultyEnum.easy ->  "Easy"
        DifficultyEnum.medium -> "Medium"
        DifficultyEnum.hard ->  "Hard"
        else -> {""}
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardDescription(//dialog function that displays when a card is clicked, displays entirety of the card including description in a scrollable column
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    quest: Quest?,
    calculateExp: (DifficultyEnum, Long) -> Long,
    getLevel: () -> Long,
    getCurrentExp: () -> Long,
    getExpTillLevelUp: () -> Long,
) {
    Dialog(onDismissRequest = {//dialog box created goes away when a quest is no longer selected
        selectQuest(null)
        isQuestSelected()
    }
    ) {
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
                    FlowColumn(//flow column to allow for large descriptions to be displayed
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .weight(1f),
                        ) {
                        Text(
                            quest!!.header,//displays header
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Category: ${quest.category}")
                        Text("Difficulty: ${getDifficulty(quest.exp)}", fontSize = 15.sp)//displays difficulty

                        if (quest.date.isNotEmpty()) {//checks if quest has a date
                            Text(
                                text = "Date: ${quest.date}",//displays date
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                        if (quest.time.isNotEmpty()) {//checks if quest has a time
                            Text(
                                text = "Time: ${quest.time}",//displays time
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                        if(calculateExp(quest.exp, getLevel()) + getCurrentExp() >= getExpTillLevelUp()){
                            Text(text = "+${calculateExp(quest.exp, getLevel())} Exp (LEVEL UP)", modifier = Modifier.padding(top = 5.dp, start = 5.dp), color = Color(0xFFFF7700))//displays amount of exp given on quest completion
                        }
                        else{
                            Text(text = "+${calculateExp(quest.exp, getLevel())} Exp", modifier = Modifier.padding(top = 5.dp, start = 5.dp), color = Color(0xFFFF7700))//displays amount of exp given on quest completion
                        }

                        if (quest.description.isNotEmpty()) {//checks if quest has a description
                            Text(
                                text = "Description: ${quest.description}",//displays description
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                        }

                }
            }
        }
    }
}
