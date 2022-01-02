package im.koala.data.api.response

data class ResponseWrapper<T>(
    val body: T,
    val code: Int
)