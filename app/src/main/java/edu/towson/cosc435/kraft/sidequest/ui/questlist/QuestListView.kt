package edu.towson.cosc435.kraft.sidequest.ui.questlist
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestListView(
    quests: List<Quest>,
    onToggle: (Quest, StatusEnum) -> Unit,
    onAddQuest: () -> Unit,
    onPassQuest: (Quest) -> Unit,
    onDeleteQuest: (Quest) -> Unit,
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    getSelectedQuest: () -> Quest?,
    getLevel: () -> Long,
    getCurrentExp: () -> Long,
    getExpTillLevelUp: () -> Long,
    calculateExp: (DifficultyEnum, Long) -> Long,
    onFilterQuest: (String) -> Unit,
) {
    var searching by remember { mutableStateOf(false) }//if true then search bar is displayed
    var searchText: String by remember { mutableStateOf("") } //string that is used to filter quests
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 75.dp)
                .background(
                    Color.Black.copy(
                        alpha =
                        if (isQuestSelected()) //changes color if quest is selected to display description
                            0.5f
                        else
                            0f
                    )
                )
        ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { searching = true }) { //if clicked searching = true then search bar displays
                        Icon(
                            imageVector = Icons.Filled.Search, //search icon
                            contentDescription = null
                        )
                    }
                    if(searching) {//if statement to check if searching is true to display searchbar
                        OutlinedTextField(
                            value = searchText,//sets value to the search filter variable searchText
                            onValueChange = {
                                searchText = it //searchText changes with user input
                            }
                        )
                        onFilterQuest(searchText) //calls onFilterQuest to filter the List based on what is entered in the searchbar
                        IconButton(onClick = {
                            searching = false //if back icon is clicked searchbar is no longer shown
                            searchText = "" //rests searchText to empty string when back button is clicked
                            onFilterQuest(searchText)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack, //back icon display
                                contentDescription = null
                            )
                        }
                    }
                }

            Text(
                text ="Level: ${getLevel()}", //displays current level at top of screen from getlevel()
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )

            Text(
                text = "EXP: ${getCurrentExp()}/${getExpTillLevelUp()}",//displays currentexp over exp till level up through respective getters
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT && quests.isNotEmpty()) { //if quest are not empty then displays list of quests
                LazyColumn() {
                    items(quests) {quest -> //displays all quests in the form of cards from QuestRow
                        QuestRow(quest, onToggle, onPassQuest, onDeleteQuest, selectQuest, isQuestSelected, getSelectedQuest, calculateExp, getLevel, getCurrentExp,getExpTillLevelUp)
                    }
                }
            } else {//this is the case where there is no quests in list
                if(searchText == ""){ //if there is no filter
                    Card( //card is displayed when no quests and no filter
                        modifier = Modifier
                            .padding(20.dp)
                            .clickable(onClick = { //when card is clicked onAddQuest() to navigate to add quest
                                onAddQuest()
                            })
                            .fillMaxWidth()
                            .height(140.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                    ){
                        Text(text = "Add a quest", fontSize = 25.sp, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth() )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                Icons.Default.Add, "",//add icon displayed on card
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "No Quests in Filter",//displays when there is a filter and there is no quests that match the filter
                            color = Color(0xFFDE5454)
                        )
                    }

                }

            }
        }
    }
}

