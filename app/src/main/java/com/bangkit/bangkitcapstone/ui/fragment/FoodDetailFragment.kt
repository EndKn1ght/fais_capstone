package com.bangkit.bangkitcapstone.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentFoodDetailBinding
import com.bangkit.bangkitcapstone.ui.adapter.SectionChartAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foodDetailImage.setImageResource(R.drawable.dummy_image)

        binding.backButtonDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        setViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setViewPager(){
        val sectionsPagerAdapter = SectionChartAdapter(requireActivity() as AppCompatActivity)
        val viewPager: ViewPager2 = binding.viewPagerDetail
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayoutDetail
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.macronutrients,
            R.string.micronutrients,
        )
    }

}