package app.waste2wealth.com.dashboard

import android.Manifest
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.communities.ui.communitiesItems
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.challengesList
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.CardTextColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import app.waste2wealth.com.utils.AutoResizedText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlin.system.exitProcess


@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NewDashboard(
    navController: NavHostController,
    viewModel: LocationViewModel = hiltViewModel(),
    email: String,
    name: String,
    pfp: String
) {
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
    var address by remember {
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
        mutableStateOf("")
    }
    var pointsRedeemed by remember {
        mutableStateOf("")
    }
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finishAndRemoveTask()
        exitProcess(0)
    }
    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    address = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned.toString()
                    pointsRedeemed = i.pointsRedeemed.toString()
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appBackground)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(0.dp, 0.dp, 50.dp, 50.dp))
                    .fillMaxWidth(),
                backgroundColor = CardColor,

                ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 45.dp,
                                bottom = 15.dp,
                                end = 25.dp,
                                start = 25.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            AutoResizedText(
                                text = "Welcome Back",
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontFamily = monteSB,
                                modifier = Modifier.padding(bottom = 7.dp)
                            )
                            AutoResizedText(
                                text = name,
                                color = CardTextColor,
                                fontSize = 20.sp,
                                fontFamily = monteBold,
                                modifier = Modifier.padding(bottom = 7.dp)
                            )
                            AutoResizedText(
                                text = "Start making a difference today!",
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontFamily = monteSB,
                                modifier = Modifier.padding(bottom = 7.dp)
                            )
                        }
                        ProfileImage(
                            imageUrl = pfp,
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .clip(CircleShape)
                                .clickable {
                                    navController.navigate(Screens.Profile.route)
                                }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, start = 25.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .offset(x = (-15).dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AutoResizedText(
                                text = "Points Earned",
                                color = CardTextColor,
                                fontSize = 14.sp,
                                fontFamily = monteBold,
                                softWrap = true,
                                modifier = Modifier.padding(start = 7.dp)
                            )
                            Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.coins),
                                    contentDescription = "coins",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 5.dp),
                                    tint = Color.Unspecified
                                )
                                AutoResizedText(
                                    text = pointsEarned,
                                    color = CardTextColor,
                                    fontSize = 15.sp,
                                    fontFamily = monteNormal,
                                )
                            }

                        }
                        Column(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .offset(x = (-15).dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AutoResizedText(
                                text = "Points Redeemed",
                                color = CardTextColor,
                                fontSize = 14.sp,
                                fontFamily = monteBold,
                                softWrap = true,
                                modifier = Modifier.padding(start = 7.dp)
                            )
                            Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.coins),
                                    contentDescription = "coins",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 5.dp),
                                    tint = Color.Unspecified
                                )
                                AutoResizedText(
                                    text = pointsRedeemed,
                                    color = CardTextColor,
                                    fontSize = 15.sp,
                                    fontFamily = monteNormal,
                                )
                            }


                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }



            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    AutoResizedText(
                        text = "Current Progress",
                        color = textColor,
                        fontSize = 20.sp,
                        fontFamily = monteBold,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                    AutoResizedText(
                        text = "200 more points to reach weekly target",
                        color = textColor,
                        fontSize = 9.sp,
                        fontFamily = monteBold,
                        modifier = Modifier.padding(start = 0.dp)
                    )
                }

                ArcComposable(
                    modifier = Modifier.padding(end = 25.dp),
                    text = "50%"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Card(
                    modifier = Modifier
                        .padding(5.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(
                            imageUrl = R.drawable.ic_reportwaste,
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .clip(CircleShape)
                                .clickable {
                                    navController.navigate(Screens.ReportWaste.route)
                                }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        AutoResizedText(
                            text = "Report Waste",
                            color = textColor,
                            fontSize = 13.sp,
                            fontFamily = monteNormal,
                            softWrap = true
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(5.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(
                            imageUrl = R.drawable.ic_collectwaste,
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .clip(CircleShape)
                                .clickable {
                                    navController.navigate(Screens.CollectWasteLists.route)
                                }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        AutoResizedText(
                            text = "Collect Waste",
                            color = textColor,
                            fontSize = 13.sp,
                            fontFamily = monteNormal,
                            softWrap = true
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(5.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(
                            imageUrl = R.drawable.ic_rewards,
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .clip(CircleShape)
                                .clickable {
                                    navController.navigate(Screens.Rewards.route)
                                }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        AutoResizedText(
                            text = "Rewards",
                            color = textColor,
                            fontSize = 13.sp,
                            fontFamily = monteNormal,
                            softWrap = true
                        )
                    }
                }

            }


            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoResizedText(
                    text = "Upcoming Community Events",
                    color = textColor,
                    fontSize = 15.sp
                )

                AutoResizedText(
                    text = "All Events",
                    color = textColor,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.Community.route)
                    }
                )
            }

            LazyRow(contentPadding = PaddingValues(10.dp)) {
                items(communitiesItems.take(3)) { item ->
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .padding(end = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 10.dp,
                        backgroundColor = CardColor
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ProfileImage(
                                imageUrl = item.image,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .fillMaxHeight()
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = item.title,
                                    color = CardTextColor,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    softWrap = true
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = item.time,
                                    color = CardTextColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {

                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = appBackground
                                    )
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.LocationOn,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp),
                                            tint = CardColor
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        AutoResizedText(
                                            text = "Register",
                                            color = textColor,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            val lastTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                AutoResizedText(
                    text = "Waste Wise",
                    color = lastTextColor.copy(0.75f),
                    fontSize = 33.sp,
                    fontFamily = monteSB,
                )
                Spacer(modifier = Modifier.height(0.dp))
                AutoResizedText(
                    text = "Rewards Rise",
                    color = lastTextColor.copy(0.5f),
                    fontSize = 23.sp,
                    fontFamily = monteSB,
                )
                Spacer(modifier = Modifier.height(10.dp))
                AutoResizedText(
                    text = "Crafted with ❤️ by The Centennials",
                    color = lastTextColor.copy(0.75f),
                    fontSize = 10.sp,
                    fontFamily = monteSB,
                )
            }

            Spacer(modifier = Modifier.height(130.dp))

        }
    }
}

@Composable
fun ArcComposable(
    modifier: Modifier,
    text: String,
    progress: Float = 0.5f, // Progress value between 0.0 and 1.0
    completedColor: Color = Color(0xFF48c5a3),
    remainingColor: Color = Color(0xFFe4e4e4),
) {
    val sweepAngle = 180f * progress
    Box(
        modifier = modifier.background(Color.Transparent)
    ) {
        Canvas(modifier = Modifier.size(70.dp)) {
            // Draw the remaining part of the arc
            drawArc(
                color = remainingColor,
                -180f + sweepAngle,
                180f - sweepAngle,
                useCenter = false,
                size = Size(size.width, size.height),
                style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw the completed part of the arc
            drawArc(
                color = completedColor,
                -180f,
                sweepAngle,
                useCenter = false,
                size = Size(size.width, size.height),
                style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        AutoResizedText(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = text,
            color = textColor,
            fontSize = 20.sp
        )
    }
}
