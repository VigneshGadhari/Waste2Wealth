package app.waste2wealth.com.ktorClient.weather.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class HereWeather(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("current_units")
    val currentUnits: CurrentUnits?,
    @SerializedName("elevation")
    val elevation: Double?,
    @SerializedName("generationtime_ms")
    val generationtimeMs: Double?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String?,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int?
)