package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.databinding.WorkoutItemListBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity
import java.util.*

class WorkoutListAdapter(
    private val callBack: (WorkoutEntity) -> Unit,
) : ListAdapter<WorkoutEntity, WorkoutListAdapter.ListWorkoutViewHolder>(DIFF_CALLBACK) {

    private var originalList: List<WorkoutEntity> = emptyList()
    private var filteredDataList: List<WorkoutEntity> = emptyList()

    class ListWorkoutViewHolder(val binding: WorkoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: WorkoutEntity) {
            binding.itemName.text = workout.name
            binding.itemDesc.text = workout.desc
            binding.itemMet.text = workout.cal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListWorkoutViewHolder {
        val binding = WorkoutItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListWorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListWorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        if (workout != null) {
            holder.bind(workout)
        }

        holder.itemView.setOnClickListener {
            callBack(workout)
        }
    }

    fun filterList(query: String) {
        val filterPattern = query.lowercase(Locale.getDefault())

        if (originalList.isEmpty()) {
            originalList = currentList
        }

        filteredDataList = originalList.filter { item ->
            item.name.lowercase(Locale.getDefault()).contains(filterPattern)
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

        val DIFF_CALLBACK: DiffUtil.ItemCallback<WorkoutEntity> =
            object : DiffUtil.ItemCallback<WorkoutEntity>() {
                override fun areItemsTheSame(
                    oldItem: WorkoutEntity,
                    newItem: WorkoutEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: WorkoutEntity,
                    newItem: WorkoutEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}