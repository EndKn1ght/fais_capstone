package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FoodWaterHistoryItemsBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity

class WorkoutHistoryAdapter: ListAdapter<CaloriesDailyEntity, WorkoutHistoryAdapter.WorkoutHistoryViewHolder>(DIFF_CALLBACK) {

    class WorkoutHistoryViewHolder(val binding: FoodWaterHistoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: CaloriesDailyEntity) {
            binding.miniHistoryName.text = history.name
            binding.miniHistoryIntake.text = history.intake
            binding.miniHistoryDate.text = history.dateIntake

            binding.miniHistoryIcon.setImageResource(R.drawable.ic_dumbbell)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHistoryViewHolder {
        val binding =
            FoodWaterHistoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutHistoryViewHolder, position: Int) {
        val history = getItem(position)
        if (history != null) {
            holder.bind(history)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CaloriesDailyEntity> =
            object : DiffUtil.ItemCallback<CaloriesDailyEntity>() {
                override fun areItemsTheSame(
                    oldItem: CaloriesDailyEntity, newItem: CaloriesDailyEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: CaloriesDailyEntity, newItem: CaloriesDailyEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }

    }
}
