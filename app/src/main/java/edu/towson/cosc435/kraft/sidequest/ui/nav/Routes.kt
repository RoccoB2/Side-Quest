package edu.towson.cosc435.kraft.sidequest.ui.nav

sealed class Routes( val route: String) {
    object QuestList : Routes("questlist") //route to navigate to quest list page

    object AddQuest : Routes("addquest")//route to navigate to add quest page

    object Stats : Routes("stats")//route to navigate to stats page

}