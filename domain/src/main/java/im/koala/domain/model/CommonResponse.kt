package im.koala.domain.model

open class CommonResponse {
    val className: String? = null

    var errorMessage: String? = null

    val message: String? = null

    var code: Int? = null

    val httpStatus: String? = null

    val errorTrace: String? = null

    companion object {
        val UNKOWN = CommonResponse()
    }
}