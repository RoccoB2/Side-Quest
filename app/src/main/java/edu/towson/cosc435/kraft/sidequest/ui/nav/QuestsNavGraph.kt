package edu.towson.cosc435.kraft.sidequest.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import edu.towson.cosc435.kraft.sidequest.ui.newquest.NewQuestView
import edu.towson.cosc435.kraft.sidequest.ui.questlist.QuestListView
import edu.towson.cosc435.kraft.sidequest.ui.questlist.QuestListViewModel
import edu.towson.cosc435.kraft.sidequest.ui.statsPage.StatView
import edu.towson.cosc435.kraft.sidequest.ui.statsPage.StatViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuestsNavGraph(
    navController: NavHostController = rememberNavController()

) {
    val vm: QuestListViewModel = viewModel()
    val vm2: StatViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.QuestList.route
    ) {
        composable(Routes.QuestList.route) {
            val quests by vm.quests
            QuestListView(
                quests,
                onDeleteQuest=vm::deleteQuest,
                onPassQuest = {quest ->
                    vm2.addQuest(quest)
                    navController.navigate(Routes.Stats.route)
                }
            )
        }

        composable(Routes.Stats.route) {
            val stats by vm2.stats
            StatView(stats, vm2)
        }

        composable(Routes.AddQuest.route) {
            NewQuestView(onAddQuest = { quest ->
                vm.addQuest(quest)
                navController.navigate(Routes.QuestList.route)
            })
        }

    }

}