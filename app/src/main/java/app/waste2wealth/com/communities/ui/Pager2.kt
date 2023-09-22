package app.waste2wealth.com.communities.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.SwipeUp
import androidx.compose.material.icons.sharp.PrivacyTip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import app.waste2wealth.com.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.waste2wealth.com.communities.CommunitiesViewModel
import app.waste2wealth.com.components.permissions.Grapple
import app.waste2wealth.com.firebase.firestore.updateInfoToFirebase
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.reportwaste.DialogBox
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.CardTextColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import app.waste2wealth.com.utils.AutoResizedText
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

val listOfCommunities = listOf(
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Established in 2010",
        activeRegion = "Mumbai",
        name = "Green Guardians",
        description = "Championing environmental causes",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 500,
        latitude = 19.0760,
        longitude = 72.8777,
        drives = listOf(
            Drive(
                "Clean Beach Initiative",
                "Mumbai",
                "2023-10-15 10:00 AM",
                DriveStatus.UPCOMING,
                19.0759,
                72.8773
            ),
            Drive(
                "Eco-Friendly Park Cleanup",
                "Mumbai",
                "2023-11-05 09:30 AM",
                DriveStatus.UPCOMING,
                19.0762,
                72.8770
            ),
            Drive(
                "Plastic Recycling Workshop",
                "Mumbai",
                "2023-09-25 02:00 PM",
                DriveStatus.PASSED,
                19.0765,
                72.8766
            ),
            Drive(
                "Green Energy Seminar",
                "Mumbai",
                "2023-08-20 03:30 PM",
                DriveStatus.PASSED,
                19.0768,
                72.8762
            ),
            Drive(
                "Tree Planting Drive",
                "Mumbai",
                "2023-07-10 11:00 AM",
                DriveStatus.PASSED,
                19.0771,
                72.8758
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Founded in 2015",
        activeRegion = "Delhi",
        name = "Art Enthusiasts",
        description = "Promoting art and creativity",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 5,
        members = 300,
        latitude = 28.6139,
        longitude = 77.2090,
        drives = listOf(
            Drive(
                "Street Art Festival",
                "Delhi",
                "2023-09-30 11:30 AM",
                DriveStatus.UPCOMING,
                28.6136,
                77.2085
            ),
            Drive(
                "Art Exhibition",
                "Delhi",
                "2023-10-20 02:00 PM",
                DriveStatus.UPCOMING,
                28.6142,
                77.2095
            ),
            Drive(
                "Painting Workshop",
                "Delhi",
                "2023-08-15 10:00 AM",
                DriveStatus.PASSED,
                28.6130,
                77.2080
            ),
            Drive(
                "Sculpture Symposium",
                "Delhi",
                "2023-07-25 03:30 PM",
                DriveStatus.PASSED,
                28.6146,
                77.2098
            ),
            Drive(
                "Mural Painting",
                "Delhi",
                "2023-06-05 09:00 AM",
                DriveStatus.PASSED,
                28.6132,
                77.2088
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Started in 2012",
        activeRegion = "Bangalore",
        name = "Tech Wizards",
        description = "Exploring the latest tech trends",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 750,
        latitude = 12.9716,
        longitude = 77.5946,
        drives = listOf(
            Drive(
                "Tech Innovation Expo",
                "Bangalore",
                "2023-09-10 11:30 AM",
                DriveStatus.UPCOMING,
                12.9710,
                77.5940
            ),
            Drive(
                "Coding Bootcamp",
                "Bangalore",
                "2023-10-05 02:00 PM",
                DriveStatus.UPCOMING,
                12.9720,
                77.5945
            ),
            Drive(
                "AI and Robotics Seminar",
                "Bangalore",
                "2023-08-28 03:30 PM",
                DriveStatus.PASSED,
                12.9715,
                77.5935
            ),
            Drive(
                "Startup Pitch Day",
                "Bangalore",
                "2023-07-15 10:00 AM",
                DriveStatus.PASSED,
                12.9712,
                77.5948
            ),
            Drive(
                "Hackathon Challenge",
                "Bangalore",
                "2023-06-20 09:00 AM",
                DriveStatus.PASSED,
                12.9718,
                77.5942
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Established in 2016",
        activeRegion = "Chennai",
        name = "Health & Wellness",
        description = "Promoting a healthy lifestyle",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 400,
        latitude = 13.0827,
        longitude = 80.2707,
        drives = listOf(
            Drive(
                "Yoga in the Park",
                "Chennai",
                "2023-09-20 09:00 AM",
                DriveStatus.UPCOMING,
                13.0830,
                80.2705
            ),
            Drive(
                "Healthy Cooking Workshop",
                "Chennai",
                "2023-10-12 03:00 PM",
                DriveStatus.UPCOMING,
                13.0825,
                80.2710
            ),
            Drive(
                "Fitness Bootcamp",
                "Chennai",
                "2023-08-10 05:30 PM",
                DriveStatus.PASSED,
                13.0829,
                80.2702
            ),
            Drive(
                "Meditation Retreat",
                "Chennai",
                "2023-07-05 11:00 AM",
                DriveStatus.PASSED,
                13.0823,
                80.2708
            ),
            Drive(
                "Nutrition Seminar",
                "Chennai",
                "2023-06-15 02:30 PM",
                DriveStatus.PASSED,
                13.0826,
                80.2704
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Founded in 2018",
        activeRegion = "Hyderabad",
        name = "Startup Enthusiasts",
        description = "Supporting budding entrepreneurs",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 5,
        members = 600,
        latitude = 17.3850,
        longitude = 78.4867,
        drives = listOf(
            Drive(
                "Entrepreneurship Workshop",
                "Hyderabad",
                "2023-10-08 10:30 AM",
                DriveStatus.UPCOMING,
                17.3845,
                78.4872
            ),
            Drive(
                "Startup Pitch Competition",
                "Hyderabad",
                "2023-11-18 02:30 PM",
                DriveStatus.UPCOMING,
                17.3855,
                78.4862
            ),
            Drive(
                "Tech Startups Panel Discussion",
                "Hyderabad",
                "2023-09-02 04:00 PM",
                DriveStatus.PASSED,
                17.3848,
                78.4868
            ),
            Drive(
                "Investor Meetup",
                "Hyderabad",
                "2023-08-12 09:00 AM",
                DriveStatus.PASSED,
                17.3852,
                78.4865
            ),
            Drive(
                "Product Launch Event",
                "Hyderabad",
                "2023-07-22 03:00 PM",
                DriveStatus.PASSED,
                17.3842,
                78.4870
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Started in 2014",
        activeRegion = "Kolkata",
        name = "Cultural Connect",
        description = "Celebrating diverse cultures",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 350,
        latitude = 22.5726,
        longitude = 88.3639,
        drives = listOf(
            Drive(
                "International Food Festival",
                "Kolkata",
                "2023-09-25 12:00 PM",
                DriveStatus.UPCOMING,
                22.5723,
                88.3636
            ),
            Drive(
                "Cultural Dance Performance",
                "Kolkata",
                "2023-10-30 04:30 PM",
                DriveStatus.UPCOMING,
                22.5730,
                88.3642
            ),
            Drive(
                "Art and Music Exhibition",
                "Kolkata",
                "2023-08-15 11:00 AM",
                DriveStatus.PASSED,
                22.5720,
                88.3633
            ),
            Drive(
                "Heritage Walk",
                "Kolkata",
                "2023-07-28 02:30 PM",
                DriveStatus.PASSED,
                22.5729,
                88.3638
            ),
            Drive(
                "Traditional Craft Fair",
                "Kolkata",
                "2023-06-10 10:30 AM",
                DriveStatus.PASSED,
                22.5725,
                88.3640
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Established in 2011",
        activeRegion = "Pune",
        name = "Educators' Forum",
        description = "Sharing knowledge and insights",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 5,
        members = 200,
        latitude = 18.5204,
        longitude = 73.8567,
        drives = listOf(
            Drive(
                "Teacher Training Workshop",
                "Pune",
                "2023-09-12 09:30 AM",
                DriveStatus.UPCOMING,
                18.5208,
                73.8563
            ),
            Drive(
                "STEM Education Conference",
                "Pune",
                "2023-10-25 03:00 PM",
                DriveStatus.UPCOMING,
                18.5202,
                73.8569
            ),
            Drive(
                "Student Career Counseling",
                "Pune",
                "2023-08-08 11:30 AM",
                DriveStatus.PASSED,
                18.5206,
                73.8565
            ),
            Drive(
                "Educational Technology Seminar",
                "Pune",
                "2023-07-20 02:00 PM",
                DriveStatus.PASSED,
                18.5210,
                73.8561
            ),
            Drive(
                "Literacy Awareness Drive",
                "Pune",
                "2023-06-15 10:00 AM",
                DriveStatus.PASSED,
                18.5203,
                73.8567
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Founded in 2017",
        activeRegion = "Jaipur",
        name = "Heritage Preservers",
        description = "Preserving and promoting heritage",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 300,
        latitude = 26.9124,
        longitude = 75.7873,
        drives = listOf(
            Drive(
                "Historical Monument Restoration",
                "Jaipur",
                "2023-09-18 10:00 AM",
                DriveStatus.UPCOMING,
                26.9128,
                75.7870
            ),
            Drive(
                "Cultural Heritage Exhibition",
                "Jaipur",
                "2023-10-10 02:30 PM",
                DriveStatus.UPCOMING,
                26.9122,
                75.7876
            ),
            Drive(
                "Archaeological Workshop",
                "Jaipur",
                "2023-08-22 03:30 PM",
                DriveStatus.PASSED,
                26.9126,
                75.7872
            ),
            Drive(
                "Heritage Walk Tour",
                "Jaipur",
                "2023-07-12 11:30 AM",
                DriveStatus.PASSED,
                26.9130,
                75.7878
            ),
            Drive(
                "Traditional Craft Fair",
                "Jaipur",
                "2023-06-28 09:00 AM",
                DriveStatus.PASSED,
                26.9124,
                75.7874
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Started in 2013",
        activeRegion = "Ahmedabad",
        name = "Health & Fitness Enthusiasts",
        description = "Promoting a healthy lifestyle",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 4,
        members = 400,
        latitude = 23.0225,
        longitude = 72.5714,
        drives = listOf(
            Drive(
                "Yoga and Meditation Retreat",
                "Ahmedabad",
                "2023-09-20 09:00 AM",
                DriveStatus.UPCOMING,
                23.0229,
                72.5710
            ),
            Drive(
                "Healthy Cooking Workshop",
                "Ahmedabad",
                "2023-10-12 03:00 PM",
                DriveStatus.UPCOMING,
                23.0222,
                72.5718
            ),
            Drive(
                "Fitness Bootcamp",
                "Ahmedabad",
                "2023-08-10 05:30 PM",
                DriveStatus.PASSED,
                23.0226,
                72.5714
            ),
            Drive(
                "Meditation and Wellness Seminar",
                "Ahmedabad",
                "2023-07-05 11:00 AM",
                DriveStatus.PASSED,
                23.0220,
                72.5722
            ),
            Drive(
                "Nutrition and Health Expo",
                "Ahmedabad",
                "2023-06-15 02:30 PM",
                DriveStatus.PASSED,
                23.0224,
                72.5716
            )
        )
    ),
    DummyCards(
        cardColor = "#E91E63",
        dateOfEstablishment = "Founded in 2019",
        activeRegion = "Lucknow",
        name = "Literary Enthusiasts",
        description = "Celebrating the world of literature",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        ratings = 5,
        members = 350,
        latitude = 26.8467,
        longitude = 80.9462,
        drives = listOf(
            Drive(
                "Book Reading Club",
                "Lucknow",
                "2023-09-15 04:00 PM",
                DriveStatus.UPCOMING,
                26.8463,
                80.9458
            ),
            Drive(
                "Literary Festival",
                "Lucknow",
                "2023-10-22 10:30 AM",
                DriveStatus.UPCOMING,
                26.8468,
                80.9465
            ),
            Drive(
                "Poetry Slam Night",
                "Lucknow",
                "2023-08-18 06:30 PM",
                DriveStatus.PASSED,
                26.8462,
                80.9469
            ),
            Drive(
                "Author Meet and Greet",
                "Lucknow",
                "2023-07-08 01:00 PM",
                DriveStatus.PASSED,
                26.8466,
                80.9461
            ),
            Drive(
                "Classic Literature Symposium",
                "Lucknow",
                "2023-06-25 03:30 PM",
                DriveStatus.PASSED,
                26.8464,
                80.9466
            )
        )
    )
    // You can continue to add more communities here...
)


@OptIn(ExperimentalPagerApi::class, ExperimentalMotionApi::class)
@Composable
fun Pager2(
    viewModel: CommunitiesViewModel,
    progress2: MutableState<Float>,
    padding: PaddingValues,
    allCommunities: List<DummyCards>?,
    name: String,
    email: String,
    userAddress: String,
    gender: String,
    phoneNumber: String,
    organization: String,
    pointsEarned: Int,
    pointsRedeemed: Int,
    noOfTimesReported: Int,
    noOfTimesCollected: Int,
    noOfTimesActivity: Int,
    communities: MutableState<MutableList<String>>,
    isMemberships: Boolean
) {
    val pagerState = rememberPagerState()
    val selectedItempage = remember { mutableStateOf(0) }
    Log.i("Selected item page", selectedItempage.value.toString())
    val contentPadding = PaddingValues(
        start = 40.dp, end = (40.dp), top = 30.dp, bottom = padding.calculateBottomPadding() + 15.dp
    )
    val register = remember { mutableStateOf(false) }
    val currentTitle = remember { mutableStateOf("") }
    val context = LocalContext.current
    var isCOinVisible by remember {
        mutableStateOf(false)
    }

    allCommunities?.let { community ->
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                verticalAlignment = Alignment.Top,
                count = community.size,
                state = pagerState,
                modifier = Modifier,
                contentPadding = if (viewModel.expandedState.value < 0.5f) {
                    contentPadding
                } else {
                    PaddingValues(0.dp)
                },
                userScrollEnabled = viewModel.expandedState.value < 0.5f,
            ) { page: Int ->
                val progressState = remember { mutableStateOf(0f) }
                val draggableState = rememberDraggableState(onDelta = { delta ->
                    val dragProgress = -delta / 200
                    val newProgress = progressState.value + dragProgress
                    progressState.value = newProgress.coerceIn(0f, 1f)
                    viewModel.expandedState.value = newProgress.coerceIn(0f, 1f)
                    viewModel.currentPage.value = pagerState.currentPage
                })
                val draggableState2 = rememberDraggableState(onDelta = { delta ->
                    val dragProgress = -delta / 200
                    val newProgress = progress2.value + dragProgress
                    progress2.value = newProgress.coerceIn(0f, 1f)
                    viewModel.expandedState2.value = newProgress.coerceIn(0f, 1f)
                    viewModel.currentPage.value = pagerState.currentPage
                })


                val motionScene = remember {
                    context.resources.openRawResource(R.raw.motion_scene).readBytes()
                        .decodeToString()
                }
                val motionScene2 = remember {
                    context.resources.openRawResource(R.raw.scene2).readBytes().decodeToString()
                }

                MotionLayout(
                    motionScene = MotionScene(
                        content = if (viewModel.expandedState.value < 0.9f) {
                            motionScene
                        } else {
                            motionScene2
                        }
                    ),
                    progress = if (viewModel.expandedState.value < 0.9f) {
                        progressState.value
                    } else {
                        progress2.value
                    },
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Card(
                        backgroundColor =
                        CardColor, border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFFE8A87C).fromHex(community[page].cardColor)
                        ), shape = RoundedCornerShape(40.dp), modifier = Modifier
                            .layoutId("card")
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    ProfileImage(
                                        imageUrl = community[page].image,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .fillMaxHeight(if (viewModel.expandedState.value < 0.9f) 0.35f else 1f)
                                            .clip(RoundedCornerShape(30.dp))
                                            .draggable(
                                                state = draggableState,
                                                orientation = Orientation.Vertical,
                                                startDragImmediately = true,
                                            )
                                            .then(
                                                if (viewModel.expandedState.value < 0.9f)
                                                    Modifier
                                                else Modifier.rotate(-90f)
                                            ),
                                        contentScale = ContentScale.FillBounds,
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = community[page].name,
                                    color = CardTextColor,
                                    fontSize = 20.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    softWrap = true
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = "Date of Establishment",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = community[page].dateOfEstablishment,
                                    color = CardTextColor,
                                    fontSize = 15.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = "Active Region",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                AutoResizedText(
                                    text = community[page].activeRegion,
                                    color = CardTextColor,
                                    fontSize = 15.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        AutoResizedText(
                                            text = "No of Members",
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontFamily = monteBold,
                                            modifier = Modifier.padding(horizontal = 10.dp)
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        AutoResizedText(
                                            text = community[page].members.toString(),
                                            color = CardTextColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteBold,
                                            modifier = Modifier.padding(horizontal = 10.dp)
                                        )
                                    }
                                    Column {
                                        AutoResizedText(
                                            text = "Rating",
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontFamily = monteBold,
                                            modifier = Modifier.padding(horizontal = 10.dp)
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        AutoResizedText(
                                            text = "${community[page].ratings} / 5",
                                            color = CardTextColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteBold,
                                            modifier = Modifier.padding(horizontal = 10.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        register.value = true
                                        currentTitle.value = community[page].name
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = appBackground,
                                        contentColor = textColor
                                    ),
                                    shape = RoundedCornerShape(35.dp),
                                    modifier = Modifier.padding(start = 10.dp),
                                    enabled = !isMemberships
                                ) {
                                    AutoResizedText(
                                        text = if(isMemberships) "Registered" else "Register",
                                        color = textColor,
                                        fontSize = 12.sp,
                                        fontFamily = monteSB,
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        maxLines = 1,
                                        softWrap = true,
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .draggable(
                                            state = draggableState,
                                            orientation = Orientation.Vertical,
                                            startDragImmediately = true,
                                        ),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.SwipeUp,
                                        contentDescription = null,
                                        tint = CardTextColor,
                                        modifier = Modifier
                                            .padding(bottom = 4.dp)
                                            .draggable(
                                                state = draggableState,
                                                orientation = Orientation.Vertical,
                                                startDragImmediately = true,
                                            )
                                            .size(30.dp),
                                    )

                                }
                            }

                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .layoutId("bottomSheet")
                            .draggable(
                                state = draggableState2,
                                orientation = Orientation.Vertical,
                                startDragImmediately = true,
                            )
                            .padding(10.dp),
                        backgroundColor = CardColor,
                        elevation = 10.dp,
                        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .draggable(
                                            state = draggableState2,
                                            orientation = Orientation.Vertical,
                                            startDragImmediately = true,
                                        ),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Grapple(
                                        modifier = Modifier
                                            .padding(bottom = 0.dp)
                                            .requiredHeight(20.dp)
                                            .requiredWidth(55.dp)
                                            .alpha(0.22f), color = CardTextColor
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp)
                                        .draggable(
                                            state = draggableState2,
                                            orientation = Orientation.Vertical,
                                            startDragImmediately = true,
                                        ),
                                ) {
                                    AutoResizedText(
                                        text = "Drives Conducted",
                                        fontSize = 21.sp,
                                        color = CardTextColor,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 14.dp)
                                    )
                                }
                                val sortedDrives = remember {
                                    community[page].drives.sortedWith(compareByDescending<Drive> {
                                        it.status == DriveStatus.UPCOMING // Sort by upcoming drives first
                                    }.thenByDescending {
                                        it.status == DriveStatus.PASSED // Then sort by passed drives
                                    })
                                }
                                LazyCard(
                                    list = sortedDrives,
                                    viewModel = viewModel,
                                )
                            }
                        }


                    }

                }
                if (register.value) {
                    DialogBox(
                        isVisible = register.value,
                        title = "Do you want to register for this Community?",
                        description = "We will share your name and contact details with the Community",
                        tint = textColor,
                        icon = Icons.Sharp.PrivacyTip,
                        successRequest = {
                            isCOinVisible = true
                            communities.value.add(community[pagerState.currentPage].name)
                            updateInfoToFirebase(
                                context,
                                name = name,
                                email = email,
                                phoneNumber = phoneNumber,
                                gender = gender,
                                organization = organization,
                                address = userAddress,
                                pointsEarned = pointsEarned,
                                pointsRedeemed = pointsRedeemed,
                                noOfTimesReported = noOfTimesReported,
                                noOfTimesCollected = noOfTimesCollected + 1,
                                noOfTimesActivity = noOfTimesActivity,
                                communities = communities.value
                            )
                            register.value = false
                        },
                        dismissRequest = {
                            register.value = false
                        }
                    )
                }
            }
            if (isCOinVisible) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val currenanim by rememberLottieComposition(
                        spec = LottieCompositionSpec.Asset("confetti.json")
                    )
                    LottieAnimation(
                        composition = currenanim,
                        iterations = 1,
                        contentScale = ContentScale.Crop,
                        speed = 1f,
                        modifier = Modifier
                            .fillMaxSize()
                            .size(250.dp)
                    )
                }

            }
            LaunchedEffect(key1 = isCOinVisible){
                if (isCOinVisible){
                    delay(2000)
                    isCOinVisible = false
                }
            }

        }
    }

}








