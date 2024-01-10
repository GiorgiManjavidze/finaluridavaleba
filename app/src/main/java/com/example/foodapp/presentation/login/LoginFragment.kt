package com.example.foodapp.presentation.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentLoginBinding
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate), Observer, Listener {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        listeners()
        observers()
    }

    override fun listeners() {
        binding.buttonLogin.setOnClickListener {
            logIn()
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect {
                    handleLoginState(it!!)
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

    private fun logIn() {
        viewModel.onEvent(
            LoginFragmentEvents.Login(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLoginState(loginViewState: LoginViewState) {
        binding.progressBar.isVisible = loginViewState.isLoading

        loginViewState.errorMessage?.let {
            binding.root.showSnackbar(it)
            viewModel.onEvent(LoginFragmentEvents.ResetErrorMessage)
        }
    }


    private fun handleNavigationEvents(event: NavigationEvents) {
        when (event) {
            is NavigationEvents.NavigateToHome -> findNavController().navigate(
                R.id.action_loginFragment_to_homeFragment
            )

            is NavigationEvents.NavigateToRegister -> findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }


}