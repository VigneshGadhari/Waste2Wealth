package app.waste2wealth.baseUI.utils

import android.nfc.Tag
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.baseUI.theme.CardTextColor
import app.waste2wealth.baseUI.theme.appBackground
import app.waste2wealth.baseUI.theme.monteBold
import app.waste2wealth.baseUI.theme.monteSB
import app.waste2wealth.baseUI.theme.textColor

@Composable
fun WasteItemCard(
    modifier: Modifier = Modifier,
    tags: List<Tag> = emptyList(),
    locationNo: String,
    address: String,
    distance: String,
    time: String,
    isCollectedInfo: Boolean = false,
    isEllipsis: Boolean = true,
    onCollected: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    DefaultCard(
        modifier = modifier
            .padding(13.dp)
            .clickable {
                onClick()
            },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        bottom = 7.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultIcon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp)
                )

                DefaultText(
                    text = locationNo,
                    color = Color.Gray,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 7.dp, end = 15.dp)
            ) {
                DefaultText(
                    text = address,
                    color = CardTextColor,
                    maxLines = if (isEllipsis) 1 else Int.MAX_VALUE,
                    softWrap = true,
                    overflow = if (isEllipsis) TextOverflow.Ellipsis else TextOverflow.Visible
                )
            }
            if (tags.isNotEmpty()) {
                DefaultText(
                    text = "Tags",
                    color = textColor,
                    fontSize = 15.sp,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tags) { tag ->
                        TagItem(
                            item = tag,
                            modifier = Modifier,
                            isSelected = false
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        if (isCollectedInfo) onCollected() else onClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = appBackground,
                        contentColor = textColor
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = if (isCollectedInfo) "Navigate" else "Collect",
                        color = textColor,
                        fontFamily = monteSB,
                        fontSize = 10.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 7.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$distance, Reported $time",
                        color = CardTextColor.copy(0.75f),
                        fontFamily = monteBold,
                        fontSize = 10.sp
                    )

                }
            }
        }


    }

}