package com.example.foodapp.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import com.example.foodapp.domain.local.use_case.FavouriteRecipeUseCase
import com.example.foodapp.domain.remote.use_case.recipe.RecipesUseCase
import com.example.foodapp.presentation.event.detail.DetailFragmentEvent
import com.example.foodapp.presentation.mapper.toPresentation
import com.example.foodapp.presentation.state.detail.DetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private val recipesUseCase: RecipesUseCase,
    private val favouriteRecipeUseCase: FavouriteRecipeUseCase
) : ViewModel() {

    private val _detailRecipeState = MutableStateFlow(DetailViewState())
    val detailRecipeState get() = _detailRecipeState


    fun onEvent(event: DetailFragmentEvent) {
        when (event) {
            is DetailFragmentEvent.FetchRecipe -> fetchRecipe(event.itemId)
            is DetailFragmentEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is DetailFragmentEvent.AddRecipeToFavourites -> addRecipe(event.recipe)
            is DetailFragmentEvent.RemoveRecipeFromFavourites -> removeRecipe(event.recipe)
        }
    }

    private fun fetchRecipe(itemId: Int) {
        viewModelScope.launch {
            _detailRecipeState.update { it.copy(isLoading = true) }

            recipesUseCase.getDetailedRecipe(itemId = itemId).collect { resource ->
                when (resource) {
                    is ResourceApi.Success -> {
                        _detailRecipeState.update { currentState ->
                            currentState.copy(
                                recipe = resource.data.toPresentation(),
                                isLoading = false
                            )
                        }
                    }

                    is ResourceApi.Error -> updateErrorMessage(resource.errorMessage)
                }
            }
        }
    }

    private fun addRecipe(recipe: FavouriteRecipeEntity) = viewModelScope.launch {
        favouriteRecipeUseCase.addRecipe(recipe)
    }


    private fun removeRecipe(recipe: FavouriteRecipeEntity) = viewModelScope.launch {
        favouriteRecipeUseCase.deleteRecipe(recipe)
    }

    private fun updateErrorMessage(message: String?) {
        _detailRecipeState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}