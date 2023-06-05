package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.RecipesItemBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity

class RecipeListAdapter(
    private val callBack: (FoodEntity) -> Unit,
) : ListAdapter<FoodEntity, RecipeListAdapter.ListRecipeViewHolder>(
    DIFF_CALLBACK
) {

    class ListRecipeViewHolder(val binding: RecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FoodEntity) {
            binding.recipeImage.setImageResource(R.drawable.dummy_image)
            binding.recipeName.text = recipe.foodName
            binding.recipeDesc.text = recipe.foodDesc
            binding.recipeCalItem.text = recipe.foodCal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecipeViewHolder {
        val binding = RecipesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        if (recipe != null) {
            holder.bind(recipe)
        }

        holder.binding.itemLayout.setOnClickListener {
            callBack(recipe)
        }
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<FoodEntity> =
            object : DiffUtil.ItemCallback<FoodEntity>() {
                override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                    return oldItem == newItem
                }

            }

    }
}