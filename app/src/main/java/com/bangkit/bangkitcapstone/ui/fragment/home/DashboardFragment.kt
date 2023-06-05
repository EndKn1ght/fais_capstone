package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dashboardClock.format12Hour = "hh:mm a"

        binding.caloriesProgressCircular.apply {
            binding.caloriesProgressText.text = resources.getString(R.string.calories_count, progress.toInt().toString())
            onProgressChangeListener = {
                binding.caloriesProgressText.text = it.toString()
            }
        }

        binding.waterProgressCircular.apply {
            binding.waterProgressText.text = resources.getString(R.string.waters_count, progress.toInt().toString())
            onProgressChangeListener = {
                binding.waterProgressText.text = it.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}