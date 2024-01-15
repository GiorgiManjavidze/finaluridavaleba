package com.example.foodapp.presentation.screen.favourite

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FragmentFavouriteBinding
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.event.favourite.FavouriteFragmentEvents
import com.example.foodapp.presentation.event.favourite.FavouriteNavigationEvents
import com.example.foodapp.presentation.state.favourite.FavouriteViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate),
    Observer, Listener {

    private val viewModel: FavouriteFragmentViewModel by viewModels()
    private val favouritesRecyclerAdapter by lazy { FavouriteRecyclerAdapter() }
    override fun init() {
        observers()
        listeners()
        setUpRecycler()
        swipeToDelete()
    }

    override fun listeners() {
        favouritesRecyclerAdapter.onItemClick { favRecipe ->
            viewModel.onEvent(FavouriteFragmentEvents.ItemClick(favRecipe.id!!))
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouriteRecipeState.collect {
                    handleFavouriteRecipeState(state = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouriteUiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun swipeToDelete() = ItemTouchHelper(favoritesSwipeCallback)
        .attachToRecyclerView(binding.rvFavouriteRecipes)

    private fun handleFavouriteRecipeState(state: FavouriteViewState) {
        state.favouriteRecipes.let {
            favouritesRecyclerAdapter.submitList(it)
        }

        state.errorMessage.let {
            viewModel.onEvent(FavouriteFragmentEvents.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: FavouriteNavigationEvents) {
        when (event) {
            is FavouriteNavigationEvents.NavigateToDetails -> findNavController().navigate(
                FavouriteFragmentDirections.favouritesFragmentToDetailsFragment(event.id)
            )
        }
    }

    private fun setUpRecycler() = with(binding) {
        rvFavouriteRecipes.adapter = favouritesRecyclerAdapter
        viewModel.onEvent(FavouriteFragmentEvents.GetRecipes)
    }

    private val favoritesSwipeCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = favouritesRecyclerAdapter.currentList[position]
            val title = item.title
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onEvent(FavouriteFragmentEvents.SlideToDelete(title = title))
                viewModel.onEvent(FavouriteFragmentEvents.GetRecipes)
            }
        }
    }
}