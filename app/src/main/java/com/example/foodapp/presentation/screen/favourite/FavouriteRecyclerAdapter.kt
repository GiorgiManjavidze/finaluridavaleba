package com.example.foodapp.presentation.screen.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemFavouriteRecipeBinding
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity

class FavouriteRecyclerAdapter :
    ListAdapter<FavouriteRecipeEntity, FavouriteRecyclerAdapter.FavouriteViewHolder>(
        FavouriteDiffUtil()
    ) {

    private var onClick: ((FavouriteRecipeEntity) -> Unit)? = null

    inner class FavouriteViewHolder(private val binding: ItemFavouriteRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favouriteRecipeEntity: FavouriteRecipeEntity) = with(binding) {
            Glide.with(itemView.context)
                .load(favouriteRecipeEntity.image)
                .into(ivImage)
            tvTitle.text = favouriteRecipeEntity.title

            root.setOnClickListener { onClick?.invoke(favouriteRecipeEntity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavouriteViewHolder(
        ItemFavouriteRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class FavouriteDiffUtil : DiffUtil.ItemCallback<FavouriteRecipeEntity>() {
        override fun areItemsTheSame(
            oldItem: FavouriteRecipeEntity,
            newItem: FavouriteRecipeEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavouriteRecipeEntity,
            newItem: FavouriteRecipeEntity
        ) = oldItem == newItem

    }

    fun onItemClick(click: (FavouriteRecipeEntity) -> Unit) {
        this.onClick = click
    }

}