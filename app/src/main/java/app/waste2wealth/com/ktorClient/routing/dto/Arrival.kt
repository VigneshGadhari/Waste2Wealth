package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Arrival(
    @SerializedName("place")
    val place: Place?,
    @SerializedName("time")
    val time: String?
)