package com.bangkit.bangkitcapstone.ui.fragment.pager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentBarChartBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.FoodEntity
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.FoodDetailViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class BarChartFragment : Fragment() {

    private var _binding: FragmentBarChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodDetailViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: FoodEntity? = arguments?.getParcelable("data")
        setupBarChart(binding.barChart, data!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBarChart(barChart: BarChart, foodData: FoodEntity) {
        // Create a list of bar entries
        val entries = listOf(
            BarEntry(1f, foodData.vitB6, "Vitamin B6"),
            BarEntry(2f, foodData.vitC, "Vitamin C"),
            BarEntry(3f, foodData.vitA, "Vitamin A"),
            BarEntry(4f, foodData.calcium, "Calcium"),
            BarEntry(5f, foodData.iron, "Iron"),
            BarEntry(6f, foodData.zinc, "Zinc"),
            BarEntry(7f, foodData.sugar, "Sugar"),
            BarEntry(7f, foodData.sodium, "Sodium"),
            BarEntry(8f, foodData.water, "Water"),
        )

        // Create a dataset with the entries
        val dataSet = BarDataSet(entries, resources.getString(R.string.micronutrients))
        dataSet.valueTextSize = 12f
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
        dataSet.valueTextColor = Color.WHITE

        // Customize the dataset
        dataSet.color = resources.getColor(R.color.blue)

        // Create a BarData object with the dataset
        val barData = BarData(dataSet)

        // Set the bar width (optional)
        barData.barWidth = 0.5f

        // Set the bar chart data
        barChart.data = barData

        // Customize the x-axis labels
        val labels = listOf(
            "Vitamin B6,",
            "Vitamin C",
            "Vitamin A",
            "Calcium",
            "Iron",
            "Zinc",
            "Sugar",
            "Sodium",
            "Water"
        )
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        // Customize the bar chart appearance
        barChart.setDrawValueAboveBar(true)
        barChart.setFitBars(true)
        barChart.description = Description().apply {
            text = resources.getString(R.string.empty_string)
        }

        // Refresh the chart
        barChart.invalidate()
    }

}