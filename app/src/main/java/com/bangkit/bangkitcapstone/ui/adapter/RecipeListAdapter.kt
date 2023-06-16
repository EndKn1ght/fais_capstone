package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.databinding.ItemListBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity
import com.bumptech.glide.Glide
import java.util.*

class RecipeListAdapter(
    private val callBack: (FoodEntity) -> Unit,
) : ListAdapter<FoodEntity, RecipeListAdapter.ListRecipeViewHolder>(
    DIFF_CALLBACK
) {

    private var originalList: List<FoodEntity> = emptyList()
    private var filteredDataList: List<FoodEntity> = emptyList()

    class ListRecipeViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FoodEntity) {

            Glide.with(binding.root)
                .load(recipe.foodImage)
                .into(binding.itemImage)
            binding.itemName.text = recipe.foodName
            binding.itemDesc.text = recipe.foodHealth.joinToString(" ")
            binding.itemCalories.text = recipe.foodCal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecipeViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun filterList(query: String) {
        val filterPattern = query.lowercase(Locale.getDefault())

        if (originalList.isEmpty()) {
            originalList = currentList
        }

        filteredDataList = originalList.filter { item ->
            item.foodName.lowercase(Locale.getDefault()).contains(filterPattern)
        }

        submitList(filteredDataList)
    }

    fun resetFilter() {
        if (originalList.isNotEmpty()) {
            submitList(originalList)
            originalList = emptyList()
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