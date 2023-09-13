package app.waste2wealth.com.communities.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import app.waste2wealth.com.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.waste2wealth.com.communities.MainViewModel
import app.waste2wealth.com.components.permissions.Grapple
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor


val items = listOf(
    DummyCards(
        cardColor = Color(0xFF167EE7),
        date = "23th Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/images%20(1).jpeg?alt=media&token=1ea91756-543d-4651-95ad-b54f4532024a",
        title = "Dadar Beach Clean up",
        emoji = "\uD83E\uDD1D",
        type = "Weekly"
    ),
    DummyCards(
        cardColor = Color(0xFFF5F231),
        date = "21st Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        title = "Mangroves Cleanup",
        emoji = "\uD83D\uDE13",
        type = "Monthly"
    ),
    DummyCards(
        cardColor = Color(0xFFF5316F),
        date = "23th Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/images%20(1).jpeg?alt=media&token=1ea91756-543d-4651-95ad-b54f4532024a",
        title = "Dadar Beach Clean up",
        emoji = "\uD83E\uDD1D",
        type = "Weekly"
    ),
    DummyCards(
        cardColor = Color(0xFFF56F31),
        date = "23th Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/images%20(1).jpeg?alt=media&token=1ea91756-543d-4651-95ad-b54f4532024a",
        title = "Dadar Beach Clean up",
        emoji = "\uD83E\uDD1D",
        type = "Weekly"
    )
)

@OptIn(ExperimentalPagerApi::class, ExperimentalMotionApi::class)
@Composable
fun Pager2(
    viewModel: MainViewModel,
    progress2: MutableState<Float>,
    padding: PaddingValues,
) {
    val pagerState = rememberPagerState()
    val selectedItempage = remember { mutableStateOf(0) }
    Log.i("Selected item page", selectedItempage.value.toString())
    val contentPadding = PaddingValues(
        start = 40.dp, end = (40.dp), top = 30.dp, bottom = padding.calculateBottomPadding() + 15.dp
    )
    HorizontalPager(
        verticalAlignment = Alignment.Top,
        count = items.size,
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

        val context = LocalContext.current
        val motionScene = remember {
            context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
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
            Card(backgroundColor =
            appBackground, border = BorderStroke(
                width = 1.dp, color = items[page].cardColor
            ), shape = RoundedCornerShape(40.dp), modifier = Modifier
                .layoutId("card")) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ProfileImage(
                                imageUrl = items[page].image,
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
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = items[page].title,
                            color = textColor,
                            fontSize = 20.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Location",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = items[page].date,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Since",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = items[page].location,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Frequency",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = items[page].time,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFFD5065),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(35.dp),
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(
                                text = "Join Now",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = monteSB,
                                modifier = Modifier.padding(bottom = 4.dp),
                                maxLines = 1,
                                softWrap = true
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 15.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFFD5065),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text(
                                    text = "Swipe up to know more 🔒",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontFamily = monteSB,
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                        .draggable(
                                            state = draggableState,
                                            orientation = Orientation.Vertical,
                                            startDragImmediately = true,
                                        ),
                                    maxLines = 1,
                                    softWrap = true
                                )
                            }
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
                                    .alpha(0.22f), color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth().padding(start = 10.dp)
                                .draggable(
                                    state = draggableState2,
                                    orientation = Orientation.Vertical,
                                    startDragImmediately = true,
                                ),
                        ) {
                            Text(
                                text = "Drives Conducted",
                                fontSize = 21.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(start = 14.dp)
                            )
                        }
                        LazyCard(
                            list = cleanlinessDrives,
                            viewModel = viewModel,
                        )
                    }
                }


            }

        }
    }
}




