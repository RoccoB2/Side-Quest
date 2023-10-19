package edu.towson.cosc435.kraft.sidequest.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import androidx.compose.foundation.layout.*
@Composable
fun QuestRow(
    Quest: Quest,
    onToggle: (Quest) -> Unit,
    onSelectQuest: (Quest) -> Unit,
)
{
    Card(
        modifier = Modifier
            .padding(20.dp)
    ){
        Row(
            modifier = Modifier
        )
        {

        }
    }
}
