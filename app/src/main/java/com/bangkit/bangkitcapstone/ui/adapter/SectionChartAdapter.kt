package com.bangkit.bangkitcapstone.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.bangkitcapstone.ui.fragment.pager.BarChartFragment
import com.bangkit.bangkitcapstone.ui.fragment.pager.PieChartFragment

class SectionChartAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PieChartFragment()
            1 -> fragment = BarChartFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2

}