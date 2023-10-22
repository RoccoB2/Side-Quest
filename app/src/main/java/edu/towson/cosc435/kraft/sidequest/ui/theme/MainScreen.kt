package edu.towson.cosc435.kraft.sidequest.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.towson.cosc435.kraft.sidequest.ui.nav.QuestsNavGraph
import edu.towson.cosc435.kraft.sidequest.ui.nav.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val nav = rememberNavController()
    Scaffold (
        bottomBar = {
            BottomNavBar(nav = nav)
        }
    ) {
        QuestsNavGraph(nav)
    }
}

@Composable
private fun BottomNavBar(
    nav: NavHostController
) {
    NavigationBar() {
        NavigationBarItem(
            selected = nav.currentBackStackEntry?.destination?.route == Routes.AddQuest.route,
            onClick = {
                nav.navigate(Routes.Stats.route) {
                    launchSingleTop = true
                    popUpTo(Routes.Stats.route)
                }
            },
            icon = {
                Icon(Icons.Default.Person, "")
            },
            label = {
                Text("Stats Page")
            }
        )
        NavigationBarItem(
            selected = nav.currentBackStackEntry?.destination?.route == Routes.QuestList.route,
            onClick = {
                nav.navigate(Routes.QuestList.route) {
                    launchSingleTop = true
                    popUpTo(Routes.QuestList.route) { inclusive = true }
                }
            },
            icon = {
                Icon(Icons.Default.Home, "")
            },
            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = nav.currentBackStackEntry?.destination?.route == Routes.AddQuest.route,
            onClick = {
                nav.navigate(Routes.AddQuest.route) {
                    launchSingleTop = true
                    popUpTo(Routes.QuestList.route)
                }
            },
            icon = {
                Icon(Icons.Default.Add, "")
            },
            label = {
                Text("Add Quest")
            }
        )
    }
}