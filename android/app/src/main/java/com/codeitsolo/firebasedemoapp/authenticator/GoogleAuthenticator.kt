package com.codeitsolo.firebasedemoapp.authenticator

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.codeitsolo.firebasedemoapp.R
import com.codeitsolo.firebasedemoapp.data.remote.service.ApiService
import com.codeitsolo.firebasedemoapp.network.NetworkResponse
import com.codeitsolo.firebasedemoapp.data.remote.model.AuthResponse
import com.codeitsolo.firebasedemoapp.data.remote.model.GoogleLoginRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GoogleAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiService
) : Authenticator {

    private val tag: String = GoogleAuthenticator::class.java.simpleName
    private val credentialManager = CredentialManager.create(context = context)

    override suspend fun signIn(): AuthResponse? {
        if (isSignedIn()) return null
        return try {
            val result = getSignInCredential()
            handleSignIn(result = result)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Sign in failed: ${e.message}", Toast.LENGTH_LONG).show()
            if (e is CancellationException) throw e
            null
        }
    }

    override suspend fun signOut(): Boolean {
        credentialManager.clearCredentialState(request = ClearCredentialStateRequest())
        return true
    }

    override suspend fun isSignedIn(): Boolean {
        return false
    }

    private suspend fun getSignInCredential(): GetCredentialResponse {
        val serverClientId = context.getString(R.string.server_client_id)
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                credentialOption = GetSignInWithGoogleOption.Builder(serverClientId)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            context = context,
            request = request
        )
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): AuthResponse? {
        val credential = result.credential
        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(data = credential.data)
                Log.d(
                    tag,
                    """handleSignIn:
                            ID: ${tokenCredential.id}, 
                            Display Name: ${tokenCredential.displayName}, 
                            Email: ${tokenCredential.id}, 
                            Photo URL: ${tokenCredential.profilePictureUri}
                    """.trimIndent()
                )

                // Send the ID token to your server for verification
                val googleLoginRequest = GoogleLoginRequest(idToken = tokenCredential.idToken)
                when (val response = api.signInWithGoogle(googleLoginRequest)) {
                    is NetworkResponse.Success -> {
                        Log.d(tag, "handleSignIn: API call successful: ${response.data}")
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                        return response.data
                    }
                    is NetworkResponse.Error -> {
                        Log.e(tag, "handleSignIn: API call failed: ${response.message}")
                        Toast.makeText(context, "Sign in failed: ${response.message}", Toast.LENGTH_LONG).show()
                        return null
                    }
                    is NetworkResponse.Exception -> {
                        Log.e(tag, "handleSignIn: API call failed: ${response.e.message}")
                        Toast.makeText(context, "Sign in failed: ${response.e.message}", Toast.LENGTH_LONG).show()
                        return null
                    }
                }

            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
                Log.e(tag, "handleSignIn: Failed to parse Google ID token", e)
                Toast.makeText(context, "Failed to parse Google ID token", Toast.LENGTH_LONG).show()
                return null
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(tag, "handleSignIn: API call failed", e)
                Toast.makeText(context, "Sign in failed: ${e.message}", Toast.LENGTH_LONG).show()
                return null
            }
        } else {
            Log.d(tag, "handleSignIn: Credential is not of type GoogleIdTokenCredential")
            Toast.makeText(context, "Sign in failed: Unexpected credential type", Toast.LENGTH_LONG).show()
        }
        return null
    }
}