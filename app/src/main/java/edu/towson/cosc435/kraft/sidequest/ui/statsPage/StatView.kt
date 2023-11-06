package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.newquest.NewQuestViewModel
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow
import edu.towson.cosc435.kraft.sidequest.ui.theme.StatRow


@Composable
fun StatView(
    quests: List<Quest>,
    vm: StatViewModel = viewModel(),
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            val config = LocalConfiguration.current
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Column {
                    Text("Level: ${vm.getLevel()}")
                }
                LazyColumn() {
                    items(quests) {quest ->
                        StatRow(quest)
                    }
                }
            }
        }
    }
}
