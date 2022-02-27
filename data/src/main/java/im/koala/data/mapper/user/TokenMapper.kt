package im.koala.data.mapper.user

import im.koala.data.entity.TokenEntity
import im.koala.domain.model.TokenResponse

fun TokenEntity.toTokenResponse() = TokenResponse(accessToken, refreshToken)