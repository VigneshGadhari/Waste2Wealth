package app.waste2wealth.baseUI.utils

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import app.waste2wealth.baseUI.theme.CardTextColor
import app.waste2wealth.baseUI.theme.TintType
import app.waste2wealth.baseUI.theme.textColor

@Composable
fun DefaultIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tintType: TintType = TintType.Normal
) {
    val tint = when (tintType) {
        TintType.Normal -> textColor
        TintType.OnCard -> CardTextColor
    }
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}