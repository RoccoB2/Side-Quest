package edu.towson.cosc435.kraft.sidequest.ui.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import edu.towson.cosc435.kraft.sidequest.ui.newquest.NewQuestView
import edu.towson.cosc435.kraft.sidequest.ui.questlist.QuestListView
import edu.towson.cosc435.kraft.sidequest.ui.questlist.QuestListViewModel
import edu.towson.cosc435.kraft.sidequest.ui.statsPage.StatView
import edu.towson.cosc435.kraft.sidequest.ui.statsPage.StatViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            RequestPushNotificationPermissions()
            RequestAlarmPermissions()
            QuestListView(
                quests,
                onToggle = vm::toggleStatus,
                onDeleteQuest = vm::deleteQuest,
                onPassQuest = { quest ->
                    vm2.addQuest(quest)
                    navController.navigate(Routes.Stats.route)
                },
                onAddQuest = {
                    navController.navigate(Routes.AddQuest.route)
                },
                selectQuest = vm::selectQuest,
                isQuestSelected = vm::isQuestSelected,
                getSelectedQuest = vm::getSelectedQuest
            )
        }

        composable(Routes.Stats.route) {
            val stats by vm2.quest
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPushNotificationPermissions() {
    val permissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    when(permissionState.status) {
        PermissionStatus.Granted -> {

        }
        is PermissionStatus.Denied -> {
            LaunchedEffect(key1 = true) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestAlarmPermissions() {
    val permissionState = rememberPermissionState(android.Manifest.permission.SET_ALARM)
    when(permissionState.status) {
        PermissionStatus.Granted -> {

        }
        is PermissionStatus.Denied -> {
            LaunchedEffect(key1 = true) {
                permissionState.launchPermissionRequest()
            }
        }
    }

}