package com.bangkit.bangkitcapstone.ui.fragment.pager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentPieChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class PieChartFragment : Fragment() {

    private var _binding: FragmentPieChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart(binding.pieChart)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupPieChart(pieChart: PieChart) {
        // Create data entries for the chart
        val entries = listOf(
            PieEntry(40f, resources.getString(R.string.carbohydrate)),
            PieEntry(20f, resources.getString(R.string.protein)),
            PieEntry(30f, resources.getString(R.string.fat))
        )

        // Create a dataset with the entries
        val dataSet = PieDataSet(entries, "")
        dataSet.setColors(Color.rgb(255, 102, 0), Color.rgb(51, 204, 51), Color.rgb(0, 153, 204))

        // Customize value text size
        dataSet.valueTextSize = 12f
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
        dataSet.valueTextColor = Color.WHITE

        pieChart.setUsePercentValues(true)

        // Customize label text style
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setEntryLabelTextSize(12f)

        // Create a PieData object from the dataset
        val data = PieData(dataSet)

        // Set data to the PieChart
        pieChart.data = data

        // Customize the chart as needed
        pieChart.description.text = resources.getString(R.string.empty_string)
        pieChart.centerText = resources.getString(R.string.macronutrients)
        pieChart.holeRadius = 30f
        pieChart.transparentCircleRadius = 50f
        pieChart.animateXY(800, 800)

        pieChart.invalidate()  // Refresh the chart
    }
}