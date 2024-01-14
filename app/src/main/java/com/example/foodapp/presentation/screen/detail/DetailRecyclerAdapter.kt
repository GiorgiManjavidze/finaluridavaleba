package com.example.foodapp.presentation.screen.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.ItemExtendedIngredientBinding
import com.example.foodapp.presentation.model.DetailedRecipeInfo

class DetailRecyclerAdapter :
    ListAdapter<DetailedRecipeInfo.ExtendedIngredient, DetailRecyclerAdapter.DetailViewHolder>(
        ExtendedRecipeDiffUtil()
    ) {

    inner class DetailViewHolder(private val binding: ItemExtendedIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: DetailedRecipeInfo.ExtendedIngredient) = with(binding) {
            tvAisle.text = ingredient.aisle
            tvConsistency.text = ingredient.consistency
            tvName.text = ingredient.name
            tvOriginal.text = ingredient.original
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailViewHolder(
        ItemExtendedIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ExtendedRecipeDiffUtil : DiffUtil.ItemCallback<DetailedRecipeInfo.ExtendedIngredient>() {
        override fun areItemsTheSame(
            oldItem: DetailedRecipeInfo.ExtendedIngredient,
            newItem: DetailedRecipeInfo.ExtendedIngredient
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DetailedRecipeInfo.ExtendedIngredient,
            newItem: DetailedRecipeInfo.ExtendedIngredient
        ): Boolean {
            return oldItem == newItem
        }

    }

}