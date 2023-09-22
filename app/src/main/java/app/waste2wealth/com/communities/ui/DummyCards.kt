package app.waste2wealth.com.communities.ui

import androidx.compose.ui.graphics.Color

data class DummyCards(
    val cardColor: String,
    val dateOfEstablishment: String,
    val activeRegion: String,
    val name: String,
    val description: String,
    val image: String,
    val ratings: Int = 0,
    val members: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val drives: List<Drive> = emptyList()
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        0.0,
        0.0,
        emptyList()
    )
}

data class Drive(
    val name: String,
    val location: String, // Assuming location is a specific place within India
    val time: String, // You can use a more specific date/time type if needed
    val status: DriveStatus,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    constructor() : this(
        "",
        "",
        "",
        DriveStatus.UPCOMING,
        0.0,
        0.0
    )
}

enum class DriveStatus {
    UPCOMING,
    PASSED
}

fun Color.fromHex(colorString: String) =
    try {
        Color(android.graphics.Color.parseColor("#$colorString"))
    } catch (e: Exception) {
        Color(0xFFE91E63)
    }

val typeOfCommunities = listOf(
    TypeOfCommunities.ALL_COMMUNITIES.names,
    TypeOfCommunities.Memberships.names
)


enum class TypeOfCommunities(val names: String) {
    ALL_COMMUNITIES("Communities"),
    Memberships("Memberships"),
}

