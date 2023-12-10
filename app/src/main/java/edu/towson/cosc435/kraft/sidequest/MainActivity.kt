package edu.towson.cosc435.kraft.sidequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import edu.towson.cosc435.kraft.sidequest.ui.theme.MainScreen
import edu.towson.cosc435.kraft.sidequest.ui.theme.SideQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SideQuestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()//calls mainscreen
                }
            }
        }
    }
}
enum class StatusEnum {//creation of status enum
    pass, fail, pending
}
enum class DifficultyEnum {//creation of difficulty enum
    easy, medium, hard, unassigned
}

