package edu.towson.cosc435.kraft.sidequest.ui.nav

sealed class Routes( val route: String) {
    object QuestList : Routes("questlist")

    object AddQuest : Routes("addquest")

    object Stats : Routes("stats")

}