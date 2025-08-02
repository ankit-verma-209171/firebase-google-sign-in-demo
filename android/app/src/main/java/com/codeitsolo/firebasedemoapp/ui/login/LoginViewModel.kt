package com.codeitsolo.firebasedemoapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeitsolo.firebasedemoapp.authenticator.Authenticator
import com.codeitsolo.firebasedemoapp.data.local.UserPreferencesRepository
import com.codeitsolo.firebasedemoapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticator: Authenticator,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _destination = MutableLiveData<Event<Destination>>()
    val destination: LiveData<Event<Destination>> get() = _destination

    fun login() {
        Log.d("TAG", "login: Login function called")
        viewModelScope.launch {
            if (authenticator.isSignedIn()) {
                authenticator.signOut()
            } else {
                val authResponse = authenticator.signIn()
                authResponse?.let {
                    userPreferencesRepository.saveUserPreferences(
                        email = it.email,
                        accessToken = it.accessToken
                    )
                    _destination.value = Event(Destination.Profile(it.email))
                }
            }
        }
    }

    sealed interface Destination {
        data class Profile(val email: String) : Destination
    }
}