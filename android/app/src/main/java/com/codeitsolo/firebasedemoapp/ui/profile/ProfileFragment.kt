package com.codeitsolo.firebasedemoapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.codeitsolo.firebasedemoapp.R
import com.codeitsolo.firebasedemoapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs by navArgs()

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.updateEmail(args.email)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToLogin.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    directions = ProfileFragmentDirections.actionProfileFragmentToLoginFragment(),
                    navOptions = navOptions {
                        popUpTo(R.id.profileFragment) {
                            inclusive = true
                        }
                    }
                )
                viewModel.onNavigationToLoginComplete()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}