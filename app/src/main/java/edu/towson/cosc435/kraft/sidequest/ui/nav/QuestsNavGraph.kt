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


@Composable
fun QuestsNavGraph(
    navController: NavHostController = rememberNavController()

) {
    val vm: QuestListViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.QuestList.route
    ) {
        composable(Routes.QuestList.route) {
            val quests by vm.quests
            val selectedQuest by vm.selectedQuest
            QuestListView(
                quests,
                selectedQuest,
                onToggle=vm::toggleStatus,
                onFilter=vm::filter,
                onSelectedQuest=vm::selectQuest,
                onAddQuest = {}
            )
        }

        composable(Routes.AddQuest.route) {
            NewQuestView(onAddQuest = { quest ->
                vm.addQuest(quest)
                navController.navigate(Routes.QuestList.route)
            })
        }
    }

}