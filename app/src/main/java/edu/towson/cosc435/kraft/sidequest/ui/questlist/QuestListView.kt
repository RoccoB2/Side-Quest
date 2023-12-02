package edu.towson.cosc435.kraft.sidequest.ui.questlist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.kraft.sidequest.StatusEnum
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow
import kotlin.reflect.KSuspendFunction1

@Composable
fun QuestListView(
    quests: List<Quest>,
    onToggle: (Quest, StatusEnum) -> Unit,
    onPassQuest: (Quest) -> Unit,
    onDeleteQuest: (Quest) -> Unit,
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    getSelectedQuest: () -> Quest?
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 75.dp)
                .background(
                Color.Black.copy(alpha =
            if(isQuestSelected())
                0.5f
            else
                0f
            ))
        ) {
            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LazyColumn() {
                    items(quests) {quest ->
                        QuestRow(quest, onToggle, onPassQuest, onDeleteQuest, selectQuest, isQuestSelected, getSelectedQuest)
                    }
                }
            }
        }
    }
}

