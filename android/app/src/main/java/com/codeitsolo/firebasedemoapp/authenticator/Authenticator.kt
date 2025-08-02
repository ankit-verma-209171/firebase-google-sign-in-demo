package com.codeitsolo.firebasedemoapp.authenticator

import com.codeitsolo.firebasedemoapp.data.remote.model.AuthResponse

interface Authenticator {

    suspend fun signIn(): AuthResponse?

    suspend fun signOut(): Boolean

    suspend fun isSignedIn(): Boolean
}