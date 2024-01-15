package com.example.foodapp.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.remote.use_case.recipe.GetRecipeByTitleUseCase
import com.example.foodapp.presentation.event.home.HomeNavigationEvents
import com.example.foodapp.presentation.event.search.SearchFragmentEvents
import com.example.foodapp.presentation.event.search.SearchNavigationEvents
import com.example.foodapp.presentation.mapper.toPresentation
import com.example.foodapp.presentation.state.search.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val getRecipeByTitle: GetRecipeByTitleUseCase) : ViewModel(){
    private val _searchViewState = MutableStateFlow(SearchViewState(null))
    val searchViewState get() = _searchViewState

    private val _searchUiEvent = MutableSharedFlow<SearchNavigationEvents>()
    val searchUiEvent: SharedFlow<SearchNavigationEvents> get() = _searchUiEvent

    fun onEvent(event: SearchFragmentEvents) {
        viewModelScope.launch {
            when (event) {
                is SearchFragmentEvents.FetchRecipesByTitle -> fetchRecipesByTitle(event.title)
                is SearchFragmentEvents.ItemClick -> {
                    updateNavigationEvent(SearchNavigationEvents.NavigateToDetails(event.id))
                }
                is SearchFragmentEvents.ResetErrorMessage -> updateErrorMessage(null)
            }
        }
    }

    private fun fetchRecipesByTitle(title: String) {
        viewModelScope.launch {
            getRecipeByTitle(title).collect { resource ->
                _searchViewState.update { it.copy(isLoading = true) }
                when (resource) {
                    is ResourceApi.Success -> {
                        _searchViewState.update { currentState ->
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

    private suspend fun updateNavigationEvent(events: SearchNavigationEvents) =
        _searchUiEvent.emit(events)

    private fun updateErrorMessage(message: String?) {
        _searchViewState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}