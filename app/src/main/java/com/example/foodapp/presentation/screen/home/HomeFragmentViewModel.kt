package com.example.foodapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.remote.use_case.recipe.RecipesUseCase
import com.example.foodapp.presentation.event.home.HomeFragmentEvents
import com.example.foodapp.presentation.event.home.HomeNavigationEvents
import com.example.foodapp.presentation.mapper.toPresentation
import com.example.foodapp.presentation.state.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val recipesUseCase: RecipesUseCase) :
    ViewModel() {

    private val _recipeState = MutableStateFlow(HomeViewState())
    val recipeState = _recipeState.asStateFlow()

    private val _homeUiEvent = MutableSharedFlow<HomeNavigationEvents>()
    val homeUiEvent: SharedFlow<HomeNavigationEvents> get() = _homeUiEvent

    fun onEvent(event: HomeFragmentEvents) {
        viewModelScope.launch {
            when (event) {
                is HomeFragmentEvents.FetchRecipes -> fetchRecipes()
                is HomeFragmentEvents.ResetErrorMessage -> updateErrorMessage(message = null)
                is HomeFragmentEvents.FetchRecipesByTitle -> fetchRecipesByTitle(event.title)
                is HomeFragmentEvents.EditTextClick -> {
                    updateNavigationEvent(HomeNavigationEvents.NavigateToSearch)
                }

                is HomeFragmentEvents.ItemClick -> {
                    updateNavigationEvent(HomeNavigationEvents.NavigateToDetails(event.id))
                }
            }
        }
    }


    private fun fetchRecipes() {
        viewModelScope.launch {
            recipesUseCase.getRecipes().collect { resource ->
                _recipeState.update { it.copy(isLoading = true) }
                when (resource) {
                    is ResourceApi.Success -> {
                        _recipeState.update { currentState ->
                            currentState.copy(
                                recipesList = resource.data.results.map { getSearchedRecipe ->
                                    getSearchedRecipe.toPresentation()
                                },
                                isLoading = false
                            )
                        }
                    }

                    is ResourceApi.Error -> {
                        updateErrorMessage(resource.errorMessage)
                    }
                }
            }
        }
    }

    private fun fetchRecipesByTitle(title: String) {
        viewModelScope.launch {
            recipesUseCase.getRecipeByTitle(title).collect { resource ->
                _recipeState.update { it.copy(isLoading = true) }
                when (resource) {
                    is ResourceApi.Success -> {
                        _recipeState.update { currentState ->
                            currentState.copy(
                                recipesList = resource.data.results.map { searchedRecipe ->
                                    searchedRecipe.toPresentation()
                                },
                                isLoading = false
                            )
                        }
                    }

                    is ResourceApi.Error -> updateErrorMessage(resource.errorMessage)
                }
            }
        }
    }

    private suspend fun updateNavigationEvent(events: HomeNavigationEvents) =
        _homeUiEvent.emit(events)

    private fun updateErrorMessage(message: String?) {
        _recipeState.update { currentState -> currentState.copy(errorMessage = message) }
    }

}