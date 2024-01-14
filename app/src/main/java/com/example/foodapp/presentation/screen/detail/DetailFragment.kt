package com.example.foodapp.presentation.screen.detail

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.common.helper.Observer
import com.example.foodapp.presentation.event.detail.DetailFragmentEvent
import com.example.foodapp.presentation.extension.showSnackbar
import com.example.foodapp.presentation.state.detail.DetailViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate),
    Observer, Listener {
    private var isSaved = false
    private val viewModel: DetailFragmentViewModel by viewModels()
    private val extendedIngredientsAdapter by lazy { DetailRecyclerAdapter() }
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var image: String

    override fun init() {
        val userId: Int = args.id
        observers()
        listeners()
        bindRecyclerView()


        viewModel.onEvent(DetailFragmentEvent.FetchRecipe(userId))

    }

    override fun listeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivFavourite.setOnClickListener {
           viewLifecycleOwner.lifecycleScope.launch {
               handleRecipeSaving(getRecipe())
               isSaved = isSaved.not()
           }
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailRecipeState.collect { x ->
                    handleDetailRecipeState(x)
                }
            }
        }
    }


    private fun handleDetailRecipeState(state: DetailViewState) {
        binding.progressBar.isVisible = state.isLoading

        state.recipe?.let {
            binding.apply {
                image = state.recipe.image.toString()
                tvTitle.text = state.recipe.title
                tvSummary.text = state.recipe.summary
                tvLicence.text = state.recipe.license
                Glide.with(binding.root).load(it.image).into(binding.ivRecipePhoto)
            }
            extendedIngredientsAdapter.submitList(state.recipe.extendedIngredients)
        }

        state.errorMessage?.let {
            binding.root.showSnackbar(it)
            viewModel.onEvent(DetailFragmentEvent.ResetErrorMessage)
        }
    }

    private fun handleRecipeSaving(recipe: FavouriteRecipeEntity) {
        when(isSaved) {
            true -> {
                viewModel.onEvent(DetailFragmentEvent.RemoveRecipeFromFavourites(recipe))
                binding.ivFavourite.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
            }
            false -> {
                viewModel.onEvent(DetailFragmentEvent.AddRecipeToFavourites(recipe))
                binding.ivFavourite.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )
            }
        }
    }

    private fun bindRecyclerView() = with(binding) {
        rvExtendedIngredients.adapter = extendedIngredientsAdapter
    }

    private fun getRecipe() = FavouriteRecipeEntity(
        id = args.id,
        title = binding.tvTitle.text.toString(),
        image = image
    )

}