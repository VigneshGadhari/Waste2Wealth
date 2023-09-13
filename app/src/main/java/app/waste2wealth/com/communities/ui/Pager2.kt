package app.waste2wealth.com.communities.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import app.waste2wealth.com.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import app.waste2wealth.com.communities.MainViewModel
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.appBackground


val items = listOf(
    DummyCards(
        cardColor = Color(0xFF167EE7),
        name = "American Express Platinum",
    ),
    DummyCards(
        cardColor = Color(0xFFF5F231),
        name = "Visa Platinum",
    ),
    DummyCards(
        cardColor = Color(0xFFF5316F),
        name = "Visa Platinum",
    ),
    DummyCards(
        cardColor = Color(0xFFF56F31),
        name = "Visa Platinum",
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
    val selectedItemIndex = remember { mutableStateOf(0) }
    Log.i("Selected item index", selectedItemIndex.value.toString())
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
        })
        val draggableState2 = rememberDraggableState(onDelta = { delta ->
            val dragProgress = -delta / 200
            val newProgress = progress2.value + dragProgress
            progress2.value = newProgress.coerceIn(0f, 1f)
            viewModel.expandedState2.value = newProgress.coerceIn(0f, 1f)
        })

        val context = LocalContext.current
        val motionScene = remember {
            context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
        }
        val motionScene2 = remember {
            context.resources.openRawResource(R.raw.scene2).readBytes().decodeToString()
        }

        MotionLayout(
            motionScene = MotionScene(content = if (viewModel.expandedState.value < 0.9f) {
                motionScene
            } else {
                motionScene2
            }),
            progress = if (viewModel.expandedState.value < 0.9f) {
                progressState.value
            } else {
                progress2.value
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            Card(backgroundColor =
                items[page].cardColor
            , border = BorderStroke(
                width = 4.dp, color = items[page].borderColor
            ), shape = RoundedCornerShape(40.dp), modifier = Modifier
                .clickable {
                    selectedItemIndex.value = page
                }
                .layoutId("card")) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "")
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                    ) {

                        Text(
                            text = items[page].toString(),
                            color = items[page].cardColor,
                            modifier = Modifier.draggable(
                                state = draggableState,
                                orientation = Orientation.Vertical,
                                startDragImmediately = true,
                            )
                        )

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
                Text(text = "")

            }

        }
    }
}
