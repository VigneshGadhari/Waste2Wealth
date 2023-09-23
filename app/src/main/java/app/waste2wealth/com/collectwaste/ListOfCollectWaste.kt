package app.waste2wealth.com.collectwaste

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.bottombar.items
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.WasteItem
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.maps.MapScreen
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.tags.Tag
import app.waste2wealth.com.tags.TagItem
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.CardTextColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import app.waste2wealth.com.utils.AutoResizedText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import java.util.concurrent.TimeUnit
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CollectWaste(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: LocationViewModel
) {
    var allWastes by remember { mutableStateOf<List<WasteItem>?>(null) }
    var profileInfo by remember { mutableStateOf<List<ProfileInfo>?>(null) }
    LaunchedEffect(key1 = Unit) {
        viewModel.getPlaces()
    }

    JetFirestore(path = {
        collection("AllWastes")
    }, onRealtimeCollectionFetch = { values, _ ->
        allWastes = values?.getListOfObjects()

    }) {
        JetFirestore(path = {
            collection("ProfileInfo")
        }, onRealtimeCollectionFetch = { values, _ ->
            profileInfo = values?.getListOfObjects()
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 0.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIos,
                        contentDescription = "",
                        tint = textColor,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(25.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-10).dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AutoResizedText(
                            text = "Collect Waste",
                            color = textColor,
                            fontFamily = monteBold,
                            fontSize = 25.sp
                        )
                    }
                }

                val cList = listOf("List View", "Map View (Beta)")
                var tabIndex by remember { mutableStateOf(0) }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 35.dp, end = 35.dp)
                ) {
                    TabRow(
                        selectedTabIndex = tabIndex,
                        backgroundColor = appBackground,
                        contentColor = textColor,
                        divider = {
                            TabRowDefaults.Divider(
                                color = Color(0xFFF37952),
                                thickness = 1.dp
                            )
                        },
                    ) {
                        cList.forEachIndexed { index, title ->
                            Tab(text = {
                                AutoResizedText(
                                    title,
                                    softWrap = false,
                                    fontSize = 13.sp,
                                )
                            },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index }
                            )
                        }

                    }

                }
                if (tabIndex == 0) {
                    Spacer(modifier = Modifier.height(30.dp))
                    if (allWastes != null) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                bottom = 150.dp,
                                top = 40.dp
                            )
                        ) {
                            allWastes = allWastes?.sortedBy {
                                distance(
                                    viewModel.latitude,
                                    viewModel.longitude,
                                    it.latitude,
                                    it.longitude
                                )
                            }
                            itemsIndexed(allWastes ?: emptyList()) { index, wasteItem ->
                                WasteItemCard(
                                    locationNo = "Location ${index + 1}",
                                    address = wasteItem.address,
                                    distance = "${
                                        convertDistance(
                                            distance(
                                                viewModel.latitude,
                                                viewModel.longitude,
                                                wasteItem.latitude,
                                                wasteItem.longitude
                                            )
                                        )
                                    } away",
                                    time = getTimeAgo(wasteItem.timeStamp),
                                    tags = wasteItem.tag.map {
                                        it.mapWithTips()
                                    },
                                ) {
                                    viewModel.locationNo.value = "Location ${index + 1}"
                                    viewModel.address.value = wasteItem.address
                                    viewModel.distance.value = "${
                                        convertDistance(
                                            distance(
                                                viewModel.latitude,
                                                viewModel.longitude,
                                                wasteItem.latitude,
                                                wasteItem.longitude
                                            )
                                        )
                                    } away"
                                    viewModel.time.value = getTimeAgo(wasteItem.timeStamp)
                                    viewModel.wastePhoto.value = wasteItem.imagePath
                                    viewModel.theirLatitude.value = wasteItem.latitude
                                    viewModel.theirLongitude.value = wasteItem.longitude
                                    viewModel.tags.value = wasteItem.tag.map {
                                        it.mapWithTips()
                                    }
                                    println("Collected time ${viewModel.time.value}")
                                    navController.navigate(Screens.CollectWasteInfo.route)
                                }

                            }
                        }
                    }
                } else {
                    MapScreen(
                        paddingValues = paddingValues,
                        viewModel = viewModel,
                    )
                }

            }
        }


    }
}


@Composable
fun WasteItemCard(
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = CardColor,
        elevation = 5.dp
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
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp)
                )

                AutoResizedText(
                    text = locationNo,
                    color = Color.Gray,
                    fontFamily = monteSB,
                    fontSize = 14.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 7.dp, end = 15.dp)
            ) {
                AutoResizedText(
                    text = address,
                    color = CardTextColor,
                    fontFamily = monteSB,
                    fontSize = 15.sp,
                    maxLines = if (isEllipsis) 1 else Int.MAX_VALUE,
                    softWrap = true,
                    overflow = if (isEllipsis) TextOverflow.Ellipsis else TextOverflow.Visible
                )
            }
            if (tags.isNotEmpty()) {
                AutoResizedText(
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
                    AutoResizedText(
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
                    AutoResizedText(
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

private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (sin(deg2rad(lat1))
            * sin(deg2rad(lat2))
            + (cos(deg2rad(lat1))
            * cos(deg2rad(lat2))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}

fun convertDistance(km: Double): String {
    return if (km < 1) {
        "${(km * 1000).toInt()} mtr"
    } else {
        "${"%.2f".format(km)} km"
    }
}

fun getTimeAgo(timeInMillis: Long): String {
    val currentTime = System.currentTimeMillis()
    val elapsedTime = currentTime - timeInMillis

    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
    val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
    val days = TimeUnit.MILLISECONDS.toDays(elapsedTime)
    val months = TimeUnit.MILLISECONDS.toDays(elapsedTime) / 30
    val years = TimeUnit.MILLISECONDS.toDays(elapsedTime) / 365

    return when {
        years >= 1 -> "$years years ago"
        months >= 1 -> "$months months ago"
        days >= 1 -> "$days days ago"
        hours >= 1 -> "$hours hours ago"
        else -> "$minutes minutes ago"
    }
}