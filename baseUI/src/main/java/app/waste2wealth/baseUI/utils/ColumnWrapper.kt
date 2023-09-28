package app.waste2wealth.baseUI.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.waste2wealth.baseUI.theme.appBackground

@Composable
fun ColumnWrapper(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxSize().background(appBackground)) {
        content()
    }
}