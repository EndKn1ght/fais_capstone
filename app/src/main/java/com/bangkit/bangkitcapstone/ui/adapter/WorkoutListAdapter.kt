package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.ItemListBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity

class WorkoutListAdapter(
    private val callBack: (WorkoutEntity) -> Unit,
) : ListAdapter<WorkoutEntity, WorkoutListAdapter.ListWorkoutViewHolder>(DIFF_CALLBACK) {

    class ListWorkoutViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: WorkoutEntity) {
            binding.itemImage.setImageResource(R.drawable.dummy_image)
            binding.itemName.text = workout.workoutName
            binding.itemDesc.text = workout.workoutDesc
            binding.itemCalories.text = workout.workoutCal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListWorkoutViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListWorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListWorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        if (workout != null) {
            holder.bind(workout)
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