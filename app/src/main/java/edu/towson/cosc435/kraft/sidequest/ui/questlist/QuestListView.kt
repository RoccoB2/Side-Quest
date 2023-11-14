package edu.towson.cosc435.kraft.sidequest.ui.questlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow

@Composable
fun QuestListView(
    quests: List<Quest>,
    onPassQuest: (Quest) -> Unit,
    onDeleteQuest: (Quest) -> Unit,
    selectQuest: (Quest?) -> Unit,
    isQuestSelected: () -> Boolean,
    getSelectedQuest: () -> Quest?
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LazyColumn() {
                    items(quests) {quest ->
                        QuestRow(quest, onPassQuest, onDeleteQuest, selectQuest, isQuestSelected, getSelectedQuest)
                    }
                }
            }
        }
    }
}

