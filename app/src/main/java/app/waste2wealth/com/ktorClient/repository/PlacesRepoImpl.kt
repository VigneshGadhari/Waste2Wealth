package app.waste2wealth.com.ktorClient.repository

import app.waste2wealth.com.ktorClient.Resource
import app.waste2wealth.com.ktorClient.here.dto.HerePlaces
import app.waste2wealth.com.ktorClient.placesAPI.dto.Places
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class PlacesRepoImpl(private val client: HttpClient) : PlacesRepository {
    override suspend fun getPlaces(latLong: String): Resource<HerePlaces> {
        return try {
            Resource.Success(
                client.get<HerePlaces>(
                    "https://revgeocode.search.hereapi.com/v1/" +
                            "revgeocode?at=$latLong" +
                            "&apiKey=OVXwPOfMbCfNnN2Vfv3lWpZnf_MMioswgR650v5gDug"
                )
            )
        } catch (e: Exception) {
            println("API is ${e.printStackTrace()}")
            Resource.Failure(e)
        }
    }

}