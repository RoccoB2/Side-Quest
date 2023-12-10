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
    val vm: QuestListViewModel = viewModel() //view model for questlist page
    val vm2: StatViewModel = viewModel() //view model for stats page
    NavHost(
        navController = navController,
        startDestination = Routes.QuestList.route
    ) {
        composable(Routes.QuestList.route) {//navigation to the quest list page
            val quests by vm.quests //list of quests from the questlist viewmodel
            RequestPushNotificationPermissions() //request the user for permission to send push notifications
            //RequestAlarmPermissions()//request the user for permission to access alarm app
            QuestListView( //calls QuestListView function in QuestListView.kt
                quests,
                onToggle = vm::toggleStatus, //sets onToggle to the Quest List view model function toggleStatus
                onDeleteQuest = vm::deleteQuest, //sets onDeleteQuest to the Quest List view model function deleteQuest
                onPassQuest = { quest -> //sets onPassQuest to the Quest List view model function addQuest (takes in a quest)
                    vm2.addQuest(quest)
                },
                onAddQuest = {
                    navController.navigate(Routes.AddQuest.route) //onAddQuest is called when there is no quests and the user clicks on the add a quest card this naviagtes them to the add quest page
                },
                selectQuest = vm::selectQuest, //sets selectQuest to the Quest List view model function selectQuest (used for dialog when quest is clicked)
                isQuestSelected = vm::isQuestSelected, //sets QuestSelected to the Quest List view model function isQuestSelected to determine if a quest is selected(used for dialog when quest is clicked)
                getSelectedQuest = vm::getSelectedQuest,//sets getSelectedQuest to the Quest List view model function getSelectedQuest to get the selected quest (used for dialog when quest is clicked)
                calculateExp = vm::calculateExp, //sets calculateEXP to the Quest List view model function calculateExp to add EXP to the users current exp depending on the difficulty of the quest completed
                getLevel = vm2::getlevel, //sets getLevel to the Quest List view model function getLevel used to get the users level
                getCurrentExp = vm2::getCurrentExp, //sets getCurrentExp to the Quest List view model function getCurrentExp used to get the users current exp
                getExpTillLevelUp = vm2::getExpTillLevelUp, //sets getExpTillLevelUp to the Quest List view model function getExpTillLevelUp used to get the users current exp until they level up
                onFilterQuest = {
                    vm.setFilteredQuest(it) //sets onFilterQuest to the Quest List View model function setFilteredQuest this is used to filter quest from the input in the searchbar
                },
            )
        }

        composable(Routes.Stats.route) {//navigates to the stats page
            val stats by vm2.quest //sets stats to the list of quest in the Stat View Model
            StatView(stats, vm2) //calls StatView function in StatView.kt
        }

        composable(Routes.AddQuest.route) {//naviagtes to the add quest page
            NewQuestView(onAddQuest = { quest -> //sets onAddQuest to the quest list view model function addQuest
                vm.addQuest(quest)  //used to add a quest to the list
                navController.navigate(Routes.QuestList.route) //a navcontroller to navigate to the questlist page when a new quest is submitted
            })
        }

    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPushNotificationPermissions() { //function to request permission from the user to send push notifications
    val permissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS) //saves the state of the users response to the permission request
    when(permissionState.status) {
        PermissionStatus.Granted -> { //when permission is granted nothing happens right away

        }
        is PermissionStatus.Denied -> {
            LaunchedEffect(key1 = true) {
                permissionState.launchPermissionRequest() //when denied it will ask again when the app is reopened
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequestAlarmPermissions() {
//    val permissionState = rememberPermissionState(android.Manifest.permission.SET_ALARM)
//    when(permissionState.status) {
//        PermissionStatus.Granted -> {
//
//        }
//        is PermissionStatus.Denied -> {
//            LaunchedEffect(key1 = true) {
//                permissionState.launchPermissionRequest()
//            }
//        }
//    }
//
//}