package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import android.content.res.Configuration
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.kraft.sidequest.data.model.Quest
import edu.towson.cosc435.kraft.sidequest.ui.newquest.NewQuestViewModel
import edu.towson.cosc435.kraft.sidequest.ui.theme.QuestRow
import edu.towson.cosc435.kraft.sidequest.ui.theme.StatRow
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Calendar


@Composable
fun StatView(
    quests: List<Quest>,
    vm: StatViewModel = viewModel(),
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 75.dp)
        ) {
            val config = LocalConfiguration.current

            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text ="Level: ${vm.getlevel()}" ,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(modifier = Modifier.padding(end = 20.dp)) {
                            Text(
                                text = "Passed",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Easy: ${vm.getPassEasy()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Medium: ${vm.getPassMedium()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Hard: ${vm.getPassHard()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = "Total Passedd: ${vm.getPassTotal()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Column(modifier = Modifier.padding(start = 20.dp)) {
                            Text(
                                text = "Failed",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Easy: ${vm.getFailEasy()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Medium: ${vm.getFailMedium()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Hard: ${vm.getFailHard()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = "Total Failed: ${vm.getFailTotal()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    Text(
                        text = "Total Quests: ${vm.getTotalQuests()}",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Longest Streak: ${vm.getLongestStreak()}",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )

                        Text(
                            text = "Current Streak: ${vm.getCurrentStreak()}",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            vm.buttonClicked()
                            vm.getQuoteAPI()
                        },
                            enabled = !vm.buttonClick.value
                        ) {
                            Text("Get Inspirational Quote")
                        }
                        if(vm.buttonClick.value){
                            if(vm.quotes.value == null){
                                CircularProgressIndicator()
                            } else{
                                Text("${vm.quotes.value!!.q} - ${vm.quotes.value!!.a}")
                            }
                        }
                    }
                    quests.forEach{quest ->
                        StatRow(quest)
                    }
                }
            }
        }
    }
}
