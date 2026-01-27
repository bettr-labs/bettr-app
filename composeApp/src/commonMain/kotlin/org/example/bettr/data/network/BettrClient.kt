package org.example.bettr.data.network

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class BettrClient {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T, NetworkError> {
        val response = try {
            apiCall()
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

        return Result.Success(response)
    }
}