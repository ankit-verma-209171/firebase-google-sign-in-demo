package com.codeitsolo.firebasedemoapp.data.remote.service

import com.codeitsolo.firebasedemoapp.network.NetworkResponse
import com.codeitsolo.firebasedemoapp.data.remote.model.AuthResponse
import com.codeitsolo.firebasedemoapp.data.remote.model.GoogleLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/google")
    suspend fun signInWithGoogle(@Body request: GoogleLoginRequest): NetworkResponse<AuthResponse>
}