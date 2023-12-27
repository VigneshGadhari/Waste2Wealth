package app.waste2wealth.com.ktorClient.weather.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerializedName("interval")
    val interval: Int?,
    @SerializedName("temperature_2m")
    val temperature: Double?,
    @SerializedName("time")
    val time: String?
)