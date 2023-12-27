package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    @SerializedName("baseDuration")
    val baseDuration: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("length")
    val length: Int?
)