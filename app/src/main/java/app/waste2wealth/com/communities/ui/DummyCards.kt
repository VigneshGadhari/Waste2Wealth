package app.waste2wealth.com.communities.ui

import androidx.compose.ui.graphics.Color

data class DummyCards(
    val borderColor: Color = Color.White,
    val cardColor: Color,
    val date: String,
    val time: String,
    val location: String,
    val title: String,
    val image: String,
    val points: Int,
    val emoji: String,
    val type: String
)
