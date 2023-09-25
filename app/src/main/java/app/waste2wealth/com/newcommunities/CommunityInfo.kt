package app.waste2wealth.com.newcommunities

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.R
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.textColor

@Composable
fun CommunityInfo() {

    Column (modifier = Modifier.verticalScroll(rememberScrollState())){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Communities Events",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
//        ProfileImage(
//            imageUrl = community[page].image,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//                .fillMaxHeight(if (viewModel.expandedState.value < 0.9f) 0.35f else 1f)
//                .clip(RoundedCornerShape(30.dp))
////                                            .draggable(
////                                                state = draggableState,
////                                                orientation = Orientation.Vertical,
////                                                startDragImmediately = true,
////                                            )
//                .then(
//                    if (viewModel.expandedState.value < 0.9f)
//                        Modifier
//                    else Modifier.rotate(-90f)
//                ),
//            contentScale = ContentScale.FillBounds,
//        )

            Image(
                painter = painterResource(id = R.drawable.commi),
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp)

            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {


                Image(
                    painter = painterResource(id = R.drawable.commi),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)

                )

                Text(text = "Contact us", fontSize = 18.sp)
            }
        }


        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {

            CommunityColumnCard(text = "Location", text2 = "Vashi, Navi Mumbai")
            CommunityColumnCard(text = "Date", text2 = "29th Sept, 2023")
            CommunityColumnCard(text = "Time", text2 = "7am to 10am")
            CommunityColumnCard(text = "Organised by", text2 = "Rotary Club, Mumbai")

            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Text(text = "About Rotary ", fontSize = 15.sp)
                Text(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown .",
                    fontSize = 15.sp
                )
            }

        }

        Card(modifier = Modifier
            .padding(horizontal = 110.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Register",
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 3.dp)
                )


            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }



}


@Composable
fun CommunityColumnCard(text: String, text2: String) {

    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = text, fontSize = 15.sp)
        Text(text = text2, fontSize = 22.sp)
    }
}