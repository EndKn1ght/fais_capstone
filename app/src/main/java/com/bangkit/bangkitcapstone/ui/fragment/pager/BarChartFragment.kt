package com.bangkit.bangkitcapstone.ui.fragment.pager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentBarChartBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class BarChartFragment : Fragment() {

    private var _binding: FragmentBarChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBarChart(binding.barChart)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBarChart(barChart: BarChart) {
        // Create a list of bar entries
        val entries = listOf(
            BarEntry(1f, 20f, "Vitamin A"),
            BarEntry(2f, 35f, "Vitamin C"),
            BarEntry(3f, 15f, "Vitamin D"),
            BarEntry(4f, 45f, "Calcium"),
            BarEntry(5f, 30f, "Iron"),
            BarEntry(6f, 5f, "Potassium")
        )

        // Create a dataset with the entries
        val dataSet = BarDataSet(entries, resources.getString(R.string.micronutrients))
        dataSet.valueTextSize = 12f
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
        dataSet.valueTextColor = Color.WHITE

        // Customize the dataset
        dataSet.color = Color.BLUE

        // Create a BarData object with the dataset
        val barData = BarData(dataSet)

        // Set the bar width (optional)
        barData.barWidth = 0.5f

        // Set the bar chart data
        barChart.data = barData

        // Customize the x-axis labels
        val labels = listOf("Vitamin A,", "Vitamin C", "Vitamin D", "Calcium", "Iron", "Potassium")
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