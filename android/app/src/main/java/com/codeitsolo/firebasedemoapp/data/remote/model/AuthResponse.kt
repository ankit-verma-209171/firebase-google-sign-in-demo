package com.codeitsolo.firebasedemoapp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val email: String,
    val accessToken: String
)
