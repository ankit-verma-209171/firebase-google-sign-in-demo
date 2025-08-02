package com.codeitsolo.firebasedemoapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.codeitsolo.firebasedemoapp.R
import com.codeitsolo.firebasedemoapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.destination.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it) {
                    is LoginViewModel.Destination.Profile -> {
                        // Navigate to Profile Fragment
                        findNavController().navigate(
                            directions = LoginFragmentDirections.actionLoginFragmentToProfileFragment(it.email),
                            navOptions = navOptions {
                                popUpTo(R.id.loginFragment) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}