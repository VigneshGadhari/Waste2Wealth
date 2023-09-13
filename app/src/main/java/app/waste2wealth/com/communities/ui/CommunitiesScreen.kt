package app.waste2wealth.com.communities.ui

import android.Manifest
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import app.waste2wealth.com.communities.MainViewModel
import app.waste2wealth.com.profile.ProfileImage

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun CommunitiesSection(navController: NavController, email: String) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(
        if (permissionState.allPermissionsGranted) BottomDrawerValue.Closed else BottomDrawerValue.Open
    )
    var profileList by remember {
        mutableStateOf<List<ProfileInfo>?>(null)
    }
    var userAddress by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var organization by remember {
        mutableStateOf("")
    }
    var pointsEarned by remember {
        mutableStateOf(0)
    }
    var pointsRedeemed by remember {
        mutableStateOf(0)
    }
    var noOfTimesReported by remember {
        mutableStateOf(0)
    }
    var noOfTimesCollected by remember {
        mutableStateOf(0)
    }
    var noOfTimesActivity by remember {
        mutableStateOf(0)
    }
    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    userAddress = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned
                    pointsRedeemed = i.pointsRedeemed
                    noOfTimesReported = i.noOfTimesReported
                    noOfTimesCollected = i.noOfTimesCollected
                    noOfTimesActivity = i.noOfTimesActivity
                }
            }
        }
        PermissionDrawer(
            drawerState = permissionDrawerState,
            permissionState = permissionState,
            rationaleText = "To continue, allow Report Waste2Wealth to access your device's location" +
                    ". Tap Settings > Permission, and turn \"Access Location On\" on.",
            withoutRationaleText = "Location permission required for functionality of this app." +
                    " Please grant the permission.",
        ) {
            Scaffold(bottomBar = {
                BottomBar(navController = navController)
            }) {
                println(it)

                var progress2 = remember { mutableStateOf(0f) }
                val visible =
                    animateFloatAsState(if (progress2.value > 0.35f) 1f else 0f, label = "").value
                val inVisible =
                    animateFloatAsState(if (progress2.value > 0.35f) 0f else 1f, label = "").value
                val color by animateColorAsState(
                    targetValue = if (progress2.value >= 0.5f)
                        Color.Transparent else Color.Unspecified,
                    label = ""
                )

                val viewModel: MainViewModel = remember { MainViewModel() }
                Column(
                    modifier = Modifier
                        .background(appBackground)
                ) {
                    AnimatedVisibility(
                        visible = viewModel.expandedState.value < 0.5f,
                        enter = expandVertically(tween(400)) + fadeIn(tween(400)),
                        exit = shrinkVertically(tween(400)) + fadeOut(tween(400)),
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 35.dp,
                                    bottom = 35.dp,
                                    start = 20.dp,
                                    end = 20.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Community",
                                color = textColor,
                                fontSize = 35.sp,
                                fontFamily = monteBold,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp, end = 0.dp, start = 20.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.padding(end = 25.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.coins),
                                        contentDescription = "coins",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(end = 5.dp),
                                        tint = Color.Unspecified
                                    )
                                    Text(
                                        text = pointsEarned.toString(),
                                        color = textColor,
                                        fontSize = 15.sp,
                                        softWrap = true,
                                        fontFamily = monteNormal,
                                    )
                                }

                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = viewModel.expandedState.value > 0.5f,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut(),
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        if (progress2.value > 0.5f) {
                            AnimatedVisibility(
                                visible = visible > 0f,
                                enter = fadeIn() + slideInHorizontally(),
                                exit = fadeOut() + slideOutHorizontally(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 30.dp,
                                            end = 25.dp
                                        )
                                        .graphicsLayer {
                                            alpha = visible
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        backgroundColor =
                                        items[viewModel.currentPage.value].cardColor,
                                        border = BorderStroke(
                                            width = 4.dp, color = items[0].borderColor
                                        ),
                                        shape = RoundedCornerShape(30.dp),
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(100.dp)
                                    ) {
                                        ProfileImage(
                                            imageUrl = items[viewModel.currentPage.value].image,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(30.dp)),
                                        )
                                    }
                                    Text(
                                        text =  items[viewModel.currentPage.value].title,
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold,
                                        softWrap = true,
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColor,

                                    )

                                }
                            }


                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 30.dp,
                                            start = 10.dp,
                                            end = 25.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text =  items[viewModel.currentPage.value].title,
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                        softWrap = true,
                                        modifier = Modifier
                                            .graphicsLayer {
                                                alpha = inVisible
                                            },
                                        color = textColor,

                                    )

                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }

                    }
                    Pager2(viewModel, progress2, it)
                }
                Log.i("ExpandedState", "HomeScreen: ${viewModel.expandedState.value}")

            }
        }
    }
}






