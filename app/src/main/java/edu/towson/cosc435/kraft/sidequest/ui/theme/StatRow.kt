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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.StatusEnum

@Composable
fun StatRow(
    quest: Quest,
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .clickable { }
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
            Column() {
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    if(quest.status==StatusEnum.pass)
                        Text(
                            "PASS",
                            color = Color(0xFF54DE79), // green
                            fontSize = 15.sp,
                            modifier = Modifier.padding(15.dp)
                        )
                    else
                        Text(
                            "FAIL",
                            color = Color(0xFFDE5454), // red
                            fontSize = 15.sp,
                            modifier = Modifier.padding(15.dp)
                        )
                }

            }
        }
    }
}


