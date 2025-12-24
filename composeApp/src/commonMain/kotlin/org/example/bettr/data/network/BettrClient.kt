package org.example.bettr.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import org.example.bettr.data.network.model.DreamTypeDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class BettrClient(
    private val httpClient: HttpClient
) {
    suspend fun getDreams(): Result<List<DreamTypeDto>, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "https://bettr-production.up.railway.app/dreams-types"
            )
        } catch (e: ConnectTimeoutException) {
            return Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: SocketTimeoutException) {
            return Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: JsonConvertException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: ClientRequestException) {
            return when (e.response.status.value) {
                401 -> Result.Error(NetworkError.UNAUTHORIZED)
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                409 -> Result.Error(NetworkError.CONFLICT)
                413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: ServerResponseException) {
            return Result.Error(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            return Result.Error(NetworkError.NO_INTERNET)
        }

        return when (response.status.value) {
            in 200..299 -> {
                try {
                    val dreamTypes = response.body<List<DreamTypeDto>>()
                    Result.Success(dreamTypes)
                } catch (e: SerializationException) {
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            409 -> Result.Error(NetworkError.CONFLICT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}