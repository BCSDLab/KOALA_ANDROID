package im.koala.bcsd.state

sealed class Result {
    object Uninitialized : Result()

    object Loading : Result()

    data class Success<T>(val data: T) : Result()

    data class Fail<T>(val data: T) : Result()
}