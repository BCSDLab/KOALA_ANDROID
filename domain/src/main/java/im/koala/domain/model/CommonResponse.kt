package im.koala.domain.model

open class CommonResponse {
    val className: String? = null

    var errorMessage: String? = null

    val message: String? = null

    var code: Int? = null

    val httpStatus: String? = null

    val errorTrace: String? = null

    companion object {
        val UNKOWN = CommonResponse().apply { errorMessage = "오류가 발생하였습니다. 네트워크 환경을 확인해주세요" }
    }
}