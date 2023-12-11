package edu.towson.cosc435.kraft.sidequest.ui.statsPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import edu.towson.cosc435.kraft.sidequest.ui.theme.StatRow


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
                        text ="Level: ${vm.getlevel()}",//displays users level
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
                    )

                    Text(
                        text = "EXP: ${vm.getCurrentExp()}/${vm.getExpTillLevelUp()}",//displays users current exp and exp till level up
                        fontSize = 15.sp,
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
                                text = "Easy: ${vm.getPassEasy()}",//displays number of passed easu quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Medium: ${vm.getPassMedium()}",//displays number of passed medium quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Hard: ${vm.getPassHard()}", //displayed the number of passed quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = "Total Passed: ${vm.getPassTotal()}",//displays total number of passed quests
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
                                text = "Easy: ${vm.getFailEasy()}",//displays the number of failed easy quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Medium: ${vm.getFailMedium()}",//displays the number of failed medium quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = "Hard: ${vm.getFailHard()}",//displays the number of failed hard quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = "Total Failed: ${vm.getFailTotal()}",//displays the total number of failed quests
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    Text(
                        text = "Total Quests: ${vm.getTotalQuests()}",//displays the total number of quests
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
                            text = "Longest Streak: ${vm.getLongestStreak()}",//displays the longest streak of passed quests
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )

                        Text(
                            text = "Current Streak: ${vm.getCurrentStreak()}",//displays the current streak of passed quests
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
                        Button(onClick = {//button to display inspirational quote
                            vm.buttonClicked() //toggles if button was clicked
                            vm.getQuoteAPI() //retrives quote from API
                        },
                            enabled = !vm.buttonClick.value //disables button if clicked
                        ) {
                            Text(
                                text = "Get Inspirational Quote",
                                color = MaterialTheme.colorScheme.secondary
                                )
                        }
                        if(vm.buttonClick.value){
                            if(vm.quotes.value == null){
                                CircularProgressIndicator()//loading progress indicator if it takes time to display quote after button press
                            } else{
                                Text("${vm.quotes.value!!.q} - ${vm.quotes.value!!.a}")//displays the quote in format of quote - author
                            }
                        }
                    }
                    quests.forEach{quest ->
                        StatRow(quest) //displays list of quests from statviewmodel, only displays quests that are complete
                    }
                }
            }
        }
    }
}
