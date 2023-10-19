package edu.towson.cosc435.kraft.sidequest.ui.questlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow
import java.util.logging.Filter

@Composable
fun QuestListView(
    quests: List<Quest>,
    selectedQuest: Quest?,
    onToggle: (Quest) -> Unit,
    onFilter: (String) -> Unit,
    onSelectedQuest: (Quest) -> Unit,
    onAddQuest: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            SearchBar(onFilter = onFilter)
            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LazyColumn() {
                    items(quests) {quest ->
                        QuestRow(quest, onToggle, onSelectQuest)
                    }
                }
            }
        }
    }
}
