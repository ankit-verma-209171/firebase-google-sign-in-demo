package com.codeitsolo.firebasedemoapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.codeitsolo.firebasedemoapp.R
import com.codeitsolo.firebasedemoapp.data.local.UserPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val email = userPreferencesRepository.userEmail.first()
            val accessToken = userPreferencesRepository.accessToken.first()

            val destination = if (email != null && accessToken != null) {
                SplashFragmentDirections.actionSplashFragmentToProfileFragment(email)
            } else {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }

            findNavController().navigate(
                directions = destination,
                navOptions = navOptions {
                    popUpTo(R.id.splashFragment) {
                        inclusive = true
                    }
                }
            )
        }
    }
}