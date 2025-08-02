package com.codeitsolo.firebasedemoapp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    val idToken: String
)
