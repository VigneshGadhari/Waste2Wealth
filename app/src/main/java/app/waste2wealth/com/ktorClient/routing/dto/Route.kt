package app.waste2wealth.com.ktorClient.routing.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    @SerializedName("id")
    val id: String?,
    @SerializedName("sections")
    val sections: List<Section>?
)