package app.waste2wealth.baseUI.utils

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.waste2wealth.baseUI.theme.CardTextColor
import app.waste2wealth.baseUI.theme.TintType
import app.waste2wealth.baseUI.theme.monteSB
import app.waste2wealth.baseUI.theme.textColor

@Composable
fun DefaultText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = textColor,
    fontSize: TextUnit = 16.sp,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = FontWeight.Normal,
    fontFamily: FontFamily? = monteSB,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    tintType: TintType = TintType.Normal
) {
    val tint = when (tintType) {
        TintType.Normal -> textColor
        TintType.OnCard -> CardTextColor
    }
    Text(
        text = text,
        color = tint,
        fontSize = fontSize,
        fontFamily = fontFamily,
        textAlign = textAlign,
        modifier = modifier,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )

}