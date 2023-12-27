package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Departure(
    @SerializedName("place")
    val place: PlaceX?,
    @SerializedName("time")
    val time: String?
)