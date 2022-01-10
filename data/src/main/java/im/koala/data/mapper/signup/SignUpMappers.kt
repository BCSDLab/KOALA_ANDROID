package im.koala.data.mapper.signup

import im.koala.data.api.response.signup.SignUpResultResponse
import im.koala.domain.entity.signup.SignUpResult

fun SignUpResultResponse.toSignUpResult() = SignUpResult.OK(
    userId = this.id,
    accountId = this.account,
    accountEmail = this.email,
    accountNickname = this.nickname,
    userType = this.userType,
    isAuth = this.isAuth
)