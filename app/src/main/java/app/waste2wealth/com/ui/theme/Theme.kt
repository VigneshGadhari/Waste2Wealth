package app.waste2wealth.com.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import app.waste2wealth.baseUI.theme.DarkColors
import app.waste2wealth.baseUI.theme.Typography

@Composable
fun Waste2WealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}