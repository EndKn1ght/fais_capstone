package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentDashboardBinding
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.CaloriesViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CaloriesViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getUserData("bmr").observe(viewLifecycleOwner) { bmrData ->
            val data = bmrData ?: "0"
            binding.caloriesProgressCircular.apply {
                progressMax = data.toFloat()
                progress = 0f
                binding.caloriesProgressText.text =
                    resources.getString(R.string.calories_count, progress.toInt().toString(), progressMax.toInt().toString())
                onProgressChangeListener = {
                    binding.caloriesProgressText.text = it.toString()
                }
            }

            binding.waterProgressCircular.apply {
                binding.waterProgressText.text =
                    resources.getString(R.string.waters_count, progress.toInt().toString(), progressMax.toInt().toString())
                onProgressChangeListener = {
                    binding.waterProgressText.text = it.toString()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dashboardClock.format12Hour = "hh:mm a"

        binding.caloriesCardview.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_caloriesFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}