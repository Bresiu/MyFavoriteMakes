package com.android.favoritemakes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val Green500 = Color(0xFF1EB980)
val Dark = Color(0xFF5E625E)
val Error = Color(0xFFBA3A1E)
val Bright = Color(0xFFEAF4EB)

private val ColorScheme = lightColorScheme(
    primary = Green500,
    onPrimary = Bright,
    secondary = Bright,
    surface = Bright,
    onSurface = Green500,
    background = Bright,
    onBackground = Dark,
    tertiary = Dark,
    error = Error,
    onError = Bright,
)

@Composable
fun FavoriteMakesTheme(
    content: @Composable () -> Unit
) {
    rememberSystemUiController().setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}