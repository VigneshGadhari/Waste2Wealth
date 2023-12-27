package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Section(
    @SerializedName("arrival")
    val arrival: Arrival?,
    @SerializedName("departure")
    val departure: Departure?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("summary")
    val summary: Summary?,
    @SerializedName("transport")
    val transport: Transport?,
    @SerializedName("type")
    val type: String?
)