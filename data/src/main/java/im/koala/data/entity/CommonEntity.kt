package im.koala.data.entity

import com.google.gson.annotations.SerializedName

open class CommonEntity {
    @SerializedName("className")
    val className: String? = null

    @SerializedName("errorMessage")
    val errorMessage: String? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("code")
    val code: Int? = null

    @SerializedName("httpStatus")
    val httpStatus: String? = null

    @SerializedName("errorTrace")
    val errorTrace: String? = null
}