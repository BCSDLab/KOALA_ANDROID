package im.koala.data.entity

import com.google.gson.annotations.SerializedName

open class CommonEntity {
    @SerializedName("className")
    val className: String? = null

    @SerializedName("errorMessage")
    var errorMessage: String? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("code")
    var code: Int? = null

    @SerializedName("httpStatus")
    val httpStatus: String? = null

    @SerializedName("errorTrace")
    val errorTrace: String? = null
}