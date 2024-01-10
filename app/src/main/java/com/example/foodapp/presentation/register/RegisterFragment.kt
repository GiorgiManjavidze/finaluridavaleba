package com.example.foodapp.presentation.register

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentRegisterBinding
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.extensions.showSnackbar
import com.example.foodapp.presentation.login.LoginViewState
import com.example.foodapp.presentation.login.NavigationEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate), Listener, Observer {

    private val viewModel: RegisterViewModel by viewModels()

    override fun init() {
        listeners()
        observers()
    }

    override fun listeners() {
        binding.buttonRegister.setOnClickListener {
            register()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    handleRegisterState(it!!)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun register() {
        viewModel.onEvent(
            RegisterFragmentEvents.Register(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleRegisterState(registerViewState: RegisterViewState) {
        binding.progressBar.isVisible = registerViewState.isLoading

        registerViewState.errorMessage?.let {
            binding.root.showSnackbar(it)
            viewModel.onEvent(RegisterFragmentEvents.ResetErrorMessage)
        }
    }


    private fun handleNavigationEvents(event: RegisterNavigationEvents) {
        when (event) {
             is RegisterNavigationEvents.NavigateToLogin -> findNavController().navigate(
                 R.id.action_registerFragment_to_loginFragment
             )
        }
    }

}