package com.example.foodapp.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemRecipeBinding
import com.example.foodapp.presentation.model.SearchedRecipesInfo

class HomeRecyclerAdapter :
    ListAdapter<SearchedRecipesInfo.SearchedRecipe, HomeRecyclerAdapter.RecipeViewHolder>(
        RecipeDiffUtil()
    ) {

    private var onClick: ((SearchedRecipesInfo.SearchedRecipe) -> Unit)? = null

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: SearchedRecipesInfo.SearchedRecipe) = with(binding) {
            Glide.with(itemView.context)
                .load(recipe.image)
                .into(ivRecipePhoto)
            tvTitle.text = recipe.title
        }
    }


    class RecipeDiffUtil : DiffUtil.ItemCallback<SearchedRecipesInfo.SearchedRecipe>() {
        override fun areItemsTheSame(
            oldItem: SearchedRecipesInfo.SearchedRecipe,
            newItem: SearchedRecipesInfo.SearchedRecipe
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchedRecipesInfo.SearchedRecipe,
            newItem: SearchedRecipesInfo.SearchedRecipe
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder(
        ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onItemClick(click: (SearchedRecipesInfo.SearchedRecipe) -> Unit) {
        this.onClick = click
    }
}