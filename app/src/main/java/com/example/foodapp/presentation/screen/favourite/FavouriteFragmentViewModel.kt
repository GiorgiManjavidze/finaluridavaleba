package com.example.foodapp.presentation.screen.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.local.use_case.FavouriteRecipeUseCase
import com.example.foodapp.presentation.event.favourite.FavouriteFragmentEvents
import com.example.foodapp.presentation.event.favourite.FavouriteNavigationEvents
import com.example.foodapp.presentation.state.favourite.FavouriteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteFragmentViewModel @Inject constructor(private val favouriteUseCase: FavouriteRecipeUseCase) :
    ViewModel() {

    private val _favouriteRecipeState = MutableStateFlow(FavouriteViewState())
    val favouriteRecipeState get() = _favouriteRecipeState

    private val _favouriteUiEvent = MutableSharedFlow<FavouriteNavigationEvents>()
    val favouriteUiEvent get() = _favouriteUiEvent


    fun onEvent(event: FavouriteFragmentEvents) {
        viewModelScope.launch {
            when (event) {
                is FavouriteFragmentEvents.GetRecipes -> getRecipes()
                is FavouriteFragmentEvents.ResetErrorMessage -> updateErrorMessage(message = null)
                is FavouriteFragmentEvents.ItemClick -> updateNavigationEvent(
                    FavouriteNavigationEvents.NavigateToDetails(event.id)
                )

                is FavouriteFragmentEvents.SlideToDelete -> deleteRecipeByTitle(event.title)
            }
        }
    }

    private fun getRecipes() {
        viewModelScope.launch {
            try {
                _favouriteRecipeState.update {
                    it.copy(
                        favouriteRecipes = favouriteUseCase.getAllRecipes(),
                    )
                }
            } catch (e: Exception) {
                _favouriteRecipeState.update {
                    it.copy(
                        favouriteRecipes = null,
                        errorMessage = updateErrorMessage(e.message)
                    )
                }
            }
        }
    }


    private fun deleteRecipeByTitle(title: String) {
        viewModelScope.launch {
            favouriteUseCase.deleteRecipe(recipe = favouriteUseCase.getRecipe(title))
            val currentRecipes = _favouriteRecipeState.value.favouriteRecipes.orEmpty()
            val updatedRecipes = currentRecipes.filter { it.title != title }
            _favouriteRecipeState.value = _favouriteRecipeState.value.copy(
                favouriteRecipes = updatedRecipes
            )
        }
    }


    private suspend fun updateNavigationEvent(events: FavouriteNavigationEvents) =
        _favouriteUiEvent.emit(events)

    private fun updateErrorMessage(message: String?): String? {
        _favouriteRecipeState.update { currentState -> currentState.copy(errorMessage = message) }
        return message
    }
}