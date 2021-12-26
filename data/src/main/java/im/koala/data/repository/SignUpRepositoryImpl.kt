package im.koala.data.repository

import im.koala.data.module.RemoteDataSource
import im.koala.data.source.SignUpDataSource
import im.koala.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    @RemoteDataSource private val signUpRemoteDataSource: SignUpDataSource
) : SignUpRepository {
    override suspend fun checkIdDuplicate(id: String): Boolean {
        return !signUpRemoteDataSource.checkIdIsAvailable(id)
    }

    override suspend fun checkEmailDuplicate(email: String): Boolean {
        return !signUpRemoteDataSource.checkEmailIsAvailable(email)
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Boolean {
        return !signUpRemoteDataSource.checkNicknameIsAvailable(nickname)
    }

    override suspend fun signUp(
        id: String,
        password: String,
        email: String,
        nickname: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}