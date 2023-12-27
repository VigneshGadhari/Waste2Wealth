package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    @SerializedName("location")
    val location: Location?,
    @SerializedName("originalLocation")
    val originalLocation: OriginalLocation?,
    @SerializedName("type")
    val type: String?
)