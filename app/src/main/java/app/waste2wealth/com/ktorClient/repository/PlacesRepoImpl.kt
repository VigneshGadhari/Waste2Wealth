package app.waste2wealth.com.ktorClient.repository

import android.util.Log
import app.waste2wealth.com.ktorClient.Resource
import app.waste2wealth.com.ktorClient.geocoding.GeoCodes
import app.waste2wealth.com.ktorClient.here.dto.HerePlaces
import app.waste2wealth.com.ktorClient.hereSearch.HereSearchResponse
import app.waste2wealth.com.ktorClient.placesAPI.dto.Places
import app.waste2wealth.com.ktorClient.routing.dto.HereRoutes
import app.waste2wealth.com.ktorClient.weather.dto.HereWeather
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

class PlacesRepoImpl(private val client: HttpClient) : PlacesRepository {
    override suspend fun getPlaces(latLong: String): Resource<HerePlaces> {
        return try {
            Resource.Success(
                client.get<HerePlaces>(
                    ApiRoutes.reverseGeocoding +
                            "revgeocode?at=$latLong" +
                            "&apiKey=OVXwPOfMbCfNnN2Vfv3lWpZnf_MMioswgR650v5gDug"
                )
            )
        } catch (e: Exception) {
            println("API is ${e.printStackTrace()}")
            Resource.Failure(e)
        }
    }

    override suspend fun getGeocodingData(query: String): GeoCodes {
        return try {
            client.get<GeoCodes> {
                val encodedLocation = URLEncoder.encode(query, "UTF-8")
                url("${ApiRoutes.Geocoding_URL}?q=$encodedLocation" +
                        "&apiKey=OVXwPOfMbCfNnN2Vfv3lWpZnf_MMioswgR650v5gDug")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers {
                    append("Accept", "*/*")
                    append("Content-Type", "application/json")
                }
            }
        } catch (e: Exception) {
            Log.i("ApiException", e.message.toString())
            return GeoCodes(
                items = null
            )
        }
    }

    override suspend fun hereSearch(
        query: String,
        latitude: Double,
        longitude: Double,
        limit: Int,
    ): HereSearchResponse {
        return try {
            val a = client.get<HereSearchResponse> {
                val encodedLocation = URLEncoder.encode(query, "UTF-8")
                url(
                    ApiRoutes.hereSearch +
                        "?at=$latitude,$longitude&q=$encodedLocation" +
                        "&lang=en&apiKey=OVXwPOfMbCfNnN2Vfv3lWpZnf_MMioswgR650v5gDug")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers {
                    append("Accept", "*/*")
                    append("Content-Type", "application/json")
                }
            }
            println("photooooo: $a")
            return a
        } catch (e: Exception) {
            Log.i("ApiException", e.message.toString())
            return HereSearchResponse(
                items = null,
            )
        }
    }

    override suspend fun hereRoutes(
        transportMode: String,
        origin: String,
        destination: String
    ): HereRoutes {
        return try {
            val a = client.get<HereRoutes> {
                val encodeOrigin = URLEncoder.encode(origin, "UTF-8")
                val encodeDestination = URLEncoder.encode(destination, "UTF-8")
                url(
                    ApiRoutes.routing +
                            "?transportMode=$transportMode&origin=$encodeOrigin&destination=$encodeDestination" +
                            "&return=summary&apiKey=OVXwPOfMbCfNnN2Vfv3lWpZnf_MMioswgR650v5gDug")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers {
                    append("Accept", "*/*")
                    append("Content-Type", "application/json")
                }
            }
            println("photooooo: $a")
            return a
        } catch (e: Exception) {
            Log.i("ApiException", e.message.toString())
            return HereRoutes(
                routes = null
            )
        }
    }

    override suspend fun hereWeather(latitude: String, longitude: String): HereWeather {
        return try {
                val ok = ApiRoutes.weather
                val encodeLat = URLEncoder.encode(latitude, "UTF-8")
                val encodeLong = URLEncoder.encode(longitude, "UTF-8")
                val a = client.get<HereWeather> {
                    url(
                        "https://api.open-meteo.com/v1/forecast" +
                                "?latitude=$encodeLat&longitude=$encodeLong" +
                                "&current=temperature_2m&temperature_unit=celsius"
                    )
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    headers {
                        append("Accept", "*/*")
                        append("Content-Type", "application/json")
                    }
                }
                println("weatherrrrrr 1: $latitude & $longitude")
                println("weatherrrrrr: $a")
            return a
        } catch (e: Exception) {
            Log.i("ApiException", e.message.toString())
            return HereWeather(
                current = null,
                currentUnits = null,
                elevation = null,
                generationtimeMs = null,
                latitude = null,
                longitude = null,
                timezone = null,
                timezoneAbbreviation = null,
                utcOffsetSeconds = null,
            )
        }
    }


}