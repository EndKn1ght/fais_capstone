package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentFoodDetailBinding
import com.bangkit.bangkitcapstone.helper.Helper.formatListWithNumbers
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity
import com.bangkit.bangkitcapstone.ui.adapter.SectionChartAdapter
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.FoodDetailViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private var foodData: FoodEntity? = null
    private val viewModel: FoodDetailViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodData = FoodDetailFragmentArgs.fromBundle(it).foodEnity
            setupUi(foodData!!)
            setViewPager(foodData!!)
        }

        binding.backButtonDetail.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi(data: FoodEntity) {
        val listRecipe = formatListWithNumbers(data.foodRecipe)

        Glide.with(requireContext())
            .load(data.foodImage)
            .into(binding.foodDetailImage)

        binding.foodDetailName.text = data.foodName
        binding.foodDetailRecipe.text = listRecipe
        binding.foodDetailCal.text = data.foodCal
        binding.foodDetailHealth.text = data.foodHealth.joinToString(" ")

        binding.eatButton.setOnClickListener {
            viewModel.insertFood(data.foodName, data.foodCal).observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        findNavController().popBackStack()
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setViewPager(data: FoodEntity) {
        val sectionsPagerAdapter = SectionChartAdapter(requireActivity() as AppCompatActivity, data)
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