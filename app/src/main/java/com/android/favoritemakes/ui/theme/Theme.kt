package com.android.favoritemakes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val Green500 = Color(0xFF1EB980)
val Dark = Color(0xFF5E625E)
val Error = Color(0xFFBA3A1E)

private val DarkColorScheme = lightColorScheme(
    primary = Green500,
    surface = Color(0xFFEAF4EB),
    onSurface = Green500,
    background = Color(0xFFEAF4EB),
    onBackground = Dark,
    error = Error
)

@Composable
fun FavoriteMakesTheme(
    content: @Composable () -> Unit
) {
    rememberSystemUiController().setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}