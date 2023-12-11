package edu.towson.cosc435.kraft.sidequest.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    background = BackgroundDarkTheme,
    primary = WeirdGreen50,
    secondary = NavBarDarkElements,
    tertiary = Pink80,
    surface = WeirdGreen50,
    scrim = CardColorTheme
)
//Colors assigned to unused theme colors
private val lightColorScheme = lightColorScheme(
    background = BackgroundLightTheme,
    primary = WeirdGreen50,
    secondary = NavBarLightElements,
    tertiary = Pink40,
    scrim = QuestCardColor,
    surface = WeirdGreen50
)

@Composable
fun SideQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) darkColorScheme(background = BackgroundDarkTheme,
                primary = WeirdGreen50,
                secondary = NavBarDarkElements,
                tertiary = Pink80,
                surface = WeirdGreen50,
                scrim = CardColorTheme) else lightColorScheme(background = BackgroundLightTheme,
                primary = WeirdGreen50,
                secondary = NavBarLightElements,
                tertiary = Pink40,
                scrim = QuestCardColor,
                surface = WeirdGreen50)
        } //this let us change the theme

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}