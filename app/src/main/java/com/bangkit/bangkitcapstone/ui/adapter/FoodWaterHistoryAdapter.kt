package com.bangkit.bangkitcapstone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FoodWaterHistoryItemsBinding
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity

class FoodWaterHistoryAdapter :
    ListAdapter<CaloriesDailyEntity, FoodWaterHistoryAdapter.MiniHistoryViewHolder>(
        DIFF_CALLBACK
    ) {

    class MiniHistoryViewHolder(val binding: FoodWaterHistoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: CaloriesDailyEntity) {
            binding.miniHistoryName.text = history.name
            binding.miniHistoryIntake.text = history.intake
            binding.miniHistoryDate.text = history.dateIntake

            if (history.intakeType == IntakeType.WATER) {
                binding.miniHistoryIcon.setImageResource(R.drawable.ic_water)
            } else {
                binding.miniHistoryIcon.setImageResource(R.drawable.ic_lunch_dining)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniHistoryViewHolder {
        val binding =
            FoodWaterHistoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MiniHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MiniHistoryViewHolder, position: Int) {
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