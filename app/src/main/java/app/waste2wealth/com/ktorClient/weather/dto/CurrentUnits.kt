package app.waste2wealth.com.ktorClient.weather.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnits(
    @SerializedName("interval")
    val interval: String?,
    @SerializedName("temperature_2m")
    val temperature_2m: String?,
    @SerializedName("time")
    val time: String?
)