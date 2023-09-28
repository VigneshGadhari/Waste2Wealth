package app.waste2wealth.baseUI.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.waste2wealth.baseUI.theme.CardColor

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CardColor,
    contentColor: Color = contentColorFor(backgroundColor),
    shape: Shape = RoundedCornerShape(17.dp),
    border: BorderStroke? = null,
    elevation: Dp = 5.dp,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = CardColor,
        elevation = elevation,
        shape = shape,
        border = border,
        contentColor = contentColor
    ) {
        content()
    }
}

