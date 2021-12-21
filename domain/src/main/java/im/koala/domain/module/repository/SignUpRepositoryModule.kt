package im.koala.domain.module.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.domain.repository.SignUpRepository

@Module
@InstallIn(SingletonComponent::class)
class SignUpRepositoryModule {
    @Provides
    fun provideFakeRepository(): SignUpRepository {
        return object : SignUpRepository {
            override fun checkIdDuplicate(id: String): Boolean {
                return false
            }

            override fun checkEmailDuplicate(email: String): Boolean {
                return false
            }

            override fun checkNicknameDuplicate(nickname: String): Boolean {
                return false
            }

            override fun signUp(
                id: String,
                password: String,
                email: String,
                nickname: String
            ): Boolean {
                return true
            }
        }
    }
}