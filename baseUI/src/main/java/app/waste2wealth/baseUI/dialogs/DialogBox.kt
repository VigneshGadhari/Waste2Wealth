package app.waste2wealth.baseUI.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.waste2wealth.baseUI.theme.appBackground
import app.waste2wealth.baseUI.theme.backGround
import app.waste2wealth.baseUI.theme.monteNormal
import app.waste2wealth.baseUI.theme.monteSB
import app.waste2wealth.baseUI.theme.textColor

@Composable
fun DialogBox(
    isVisible: Boolean,
    tint: Color = textColor,
    icon: ImageVector = Icons.Filled.QuestionMark,
    title: String = "Are you sure you want to report this waste?",
    description: String = "Reporting a wrong location will result in a penalty of 100 points " +
            "and may also lead to a permanent ban from the app",
    successRequest: () -> Unit,
    dismissRequest: () -> Unit
) {
    if (isVisible) {

        Dialog(onDismissRequest = dismissRequest) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .background(appBackground)
                ) {

                    Image(
                        icon,
                        contentDescription = null, // decorative
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(
                            color = tint
                        ),
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(70.dp)
                            .fillMaxWidth(),

                        )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            color = textColor,
                            fontFamily = monteSB,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(top = 0.dp)
                                .fillMaxWidth(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = description,
                            textAlign = TextAlign.Center,
                            fontFamily = monteNormal,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(top = 7.dp, start = 25.dp, end = 13.dp)
                                .fillMaxWidth(),
                            color = textColor
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(backGround),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        TextButton(onClick = dismissRequest) {
                            Text(
                                "NO",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        TextButton(onClick = successRequest) {
                            Text(
                                "YES",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}
