package com.codeitsolo.firebasedemoapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.codeitsolo.firebasedemoapp.data.local.UserPreferencesRepository

data class Profile(
    val email: String = "",
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _profile = MutableLiveData(Profile(email = ""))
    val profile: LiveData<Profile> get() = _profile

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    fun updateEmail(email: String) {
        _profile.value = _profile.value?.copy(email = email)
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            userPreferencesRepository.clearUserPreferences()
            _navigateToLogin.value = true
        }
    }

    fun onNavigationToLoginComplete() {
        _navigateToLogin.value = false
    }
}