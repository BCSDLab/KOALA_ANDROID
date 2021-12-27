package im.koala.bcsd.state

sealed class NetworkState {
    object Uninitialized: NetworkState()

    object Loading: NetworkState()

    data class Success<T>(val data: T): NetworkState()

    data class Fail<T>(val data: T): NetworkState()
}
