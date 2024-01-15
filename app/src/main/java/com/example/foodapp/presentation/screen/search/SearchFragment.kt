package com.example.foodapp.presentation.screen.search

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.event.search.SearchFragmentEvents
import com.example.foodapp.presentation.event.search.SearchNavigationEvents
import com.example.foodapp.presentation.screen.home.HomeRecyclerAdapter
import com.example.foodapp.presentation.state.search.SearchViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    Listener, Observer {

    private val viewModel: SearchFragmentViewModel by viewModels()
    private val homeRecyclerAdapter by lazy { HomeRecyclerAdapter() }
    override fun init() {
        observers()
        listeners()
        setUpRecycler()
    }

    override fun listeners() {
        homeRecyclerAdapter.onItemClick { searchedRecipe ->
            viewModel.onEvent(SearchFragmentEvents.ItemClick(searchedRecipe.id))
        }

        binding.buttonSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            if (searchText.isNotBlank()) {
                viewModel.onEvent(SearchFragmentEvents.FetchRecipesByTitle(searchText))
            }
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchViewState.collect { state ->
                    handleSearchRecipeState(state)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchUiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun setUpRecycler() = with(binding) {
        rvRecipes.adapter = homeRecyclerAdapter
    }


    private fun handleSearchRecipeState(state: SearchViewState) {
        binding.progressBar.isVisible = state.isLoading

        state.recipesList?.let { recipes ->
            homeRecyclerAdapter.submitList(recipes)
        }
    }

    private fun handleNavigationEvents(event: SearchNavigationEvents) {
        when (event) {
            is SearchNavigationEvents.NavigateToDetails -> {
                findNavController().navigate(SearchFragmentDirections.toDetailedFragment(event.id))
            }

            is SearchNavigationEvents.NavigateToHome -> findNavController().popBackStack()
        }
    }
}