package im.koala.domain.state

sealed class ApiResponse {
    object Uninitialized : ApiResponse()

    object Loading : ApiResponse()

    data class Success<T>(val data: T) : ApiResponse()

    data class Fail<T>(val data: T) : ApiResponse()
}