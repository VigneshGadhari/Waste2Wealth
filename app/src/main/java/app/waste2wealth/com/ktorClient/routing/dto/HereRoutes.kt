package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class HereRoutes(
    @SerializedName("routes")
    val routes: List<Route>?
)