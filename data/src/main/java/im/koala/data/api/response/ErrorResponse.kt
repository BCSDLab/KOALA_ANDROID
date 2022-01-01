package im.koala.data.api.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException

data class ErrorResponse(
    @SerializedName("className") val className: String,
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("code") val errorCode: Int,
    @SerializedName("errorTrace") val errorTrace: String
)

fun Throwable.toErrorResponse(): ErrorResponse {
    if (this !is HttpException) throw IllegalStateException("Not HttpException")

    val body = this.response()?.errorBody()

    return Gson().getAdapter(ErrorResponse::class.java).fromJson(body?.string() ?: "")
}