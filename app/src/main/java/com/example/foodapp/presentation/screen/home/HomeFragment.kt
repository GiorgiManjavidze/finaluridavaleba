package com.example.foodapp.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.event.home.HomeFragmentEvents
import com.example.foodapp.presentation.event.home.HomeNavigationEvents
import com.example.foodapp.presentation.extension.showSnackbar
import com.example.foodapp.presentation.state.home.HomeViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), Observer,
    Listener {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val homeRecyclerAdapter by lazy { HomeRecyclerAdapter() }


    override fun init() {
        observers()
        setUpRecycler()
        listeners()

    }

    override fun listeners() {
        homeRecyclerAdapter.onItemClick { recipe ->
            viewModel.onEvent(HomeFragmentEvents.ItemClick(recipe.id))
        }
        binding.etSearch.setOnClickListener {
            viewModel.onEvent(HomeFragmentEvents.EditTextClick)
        }
    }


    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipeState.collect {
                    handleRecipeState(state = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }


    private fun setUpRecycler() = with(binding) {
        rvRecipes.adapter = homeRecyclerAdapter
        viewModel.onEvent(HomeFragmentEvents.FetchRecipes)
    }

    private fun handleRecipeState(state: HomeViewState) {
        binding.progressBar.isVisible = state.isLoading

        state.recipesList?.let {
            homeRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackbar(message = it)
            viewModel.onEvent(HomeFragmentEvents.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: HomeNavigationEvents) {
        when (event) {
            is HomeNavigationEvents.NavigateToDetails -> {
                findNavController().navigate(HomeFragmentDirections.toDetailedFragment(event.id))
            }
            is HomeNavigationEvents.NavigateToSearch -> findNavController().navigate(HomeFragmentDirections.toSearchFragment())
        }
    }
}