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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.material3.*
import androidx.compose.ui.graphics.SolidColor
import kotlin.reflect.KSuspendFunction1

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
    onSetSearch: (String) -> Unit
) {
    var searching by remember { mutableStateOf(false) }
    var searchText: String by remember { mutableStateOf("") }
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
                        if (isQuestSelected())
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
                    IconButton(onClick = { searching = true }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                    if(searching) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            }
                        )
                        onFilterQuest(searchText)
                        IconButton(onClick = {
                            searching = false
                            searchText = ""
                            onFilterQuest(searchText)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }

            Text(
                text ="Level: ${getLevel()}",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )

            Text(
                text = "EXP: ${getCurrentExp()}/${getExpTillLevelUp()}",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT && quests.isNotEmpty()) {
                LazyColumn() {
                    items(quests) {quest ->
                        QuestRow(quest, onToggle, onPassQuest, onDeleteQuest, selectQuest, isQuestSelected, getSelectedQuest, calculateExp, getLevel)
                    }
                }
            } else {
                if(searchText == ""){
                    Card(
                        modifier = Modifier
                            .padding(20.dp)
                            .clickable(onClick = {
                                onAddQuest()
                            })
                            .fillMaxWidth()
                            .height(140.dp)
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
                                Icons.Default.Add, "",
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
                            "No Quests in Filter",
                            color = Color(0xFFDE5454)
                        )
                    }

                }

            }
        }
    }
}

