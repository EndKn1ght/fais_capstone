package com.bangkit.bangkitcapstone.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity
import com.bangkit.bangkitcapstone.ui.fragment.pager.BarChartFragment
import com.bangkit.bangkitcapstone.ui.fragment.pager.PieChartFragment

class SectionChartAdapter(activity: AppCompatActivity, private val data: FoodEntity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> {
                val pieChartFragment = PieChartFragment()
                pieChartFragment.arguments = Bundle().apply {
                    putParcelable("data", data)
                }
                pieChartFragment
            }
            1 -> {
                val barChartFragment = BarChartFragment()
                barChartFragment.arguments = Bundle().apply {
                    putParcelable("data", data)
                }
                barChartFragment
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
        return fragment
    }

    override fun getItemCount(): Int = 2

}