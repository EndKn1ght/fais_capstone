package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentDashboardBinding
import com.bangkit.bangkitcapstone.helper.Helper
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.model.data.local.entity.CaloriesDailyEntity
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity
import com.bangkit.bangkitcapstone.ui.adapter.FoodWaterHistoryAdapter
import com.bangkit.bangkitcapstone.ui.adapter.WorkoutHistoryAdapter
import com.bangkit.bangkitcapstone.ui.fragment.detail.TimerFragmentDirections
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.CalculatorViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var workoutData: List<WorkoutEntity>? = null
    private lateinit var listAdapter: FoodWaterHistoryAdapter
    private lateinit var listAdapterWorkout : WorkoutHistoryAdapter

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
            val data = if (bmrData.isNullOrEmpty()) "0" else bmrData
            binding.caloriesProgressCircular.apply {
                progressMax = data.toFloat()
                viewModel.getIntakeData(IntakeType.WORKOUT, Helper.getToday())
                    .observe(viewLifecycleOwner) { workoutData ->
                        when (workoutData) {
                            is UiState.Loading -> {
                                indeterminateMode = true
                            }
                            is UiState.Success -> {
                                indeterminateMode = false
                                val sumWorkoutData =
                                    workoutData.data.mapNotNull { it.toDoubleOrNull() }.sum()
                                viewModel.getIntakeData(IntakeType.FOOD, Helper.getToday())
                                    .observe(viewLifecycleOwner) { data ->
                                        when (data) {
                                            is UiState.Loading -> {
                                                indeterminateMode = true
                                            }
                                            is UiState.Success -> {
                                                indeterminateMode = false
                                                val sum =
                                                    data.data.mapNotNull { it.toDoubleOrNull() }
                                                        .sum()
                                                progress = sum.toFloat() - sumWorkoutData.toFloat()
                                                binding.caloriesProgressText.text =
                                                    resources.getString(
                                                        R.string.calories_count,
                                                        progress.toInt().toString(),
                                                        progressMax.toInt().toString()
                                                    )
                                            }
                                            is UiState.Error -> {
                                                indeterminateMode = false
                                            }
                                        }
                                    }
                            }
                            is UiState.Error -> {
                                indeterminateMode = false
                            }
                        }
                    }
            }
        }

        viewModel.getUserData("water").observe(viewLifecycleOwner) { water ->
            val data = if (water.isNullOrEmpty()) "0" else water
            binding.waterProgressCircular.apply {
                progressMax = data.toFloat()
                viewModel.getIntakeData(IntakeType.WATER, Helper.getToday())
                    .observe(viewLifecycleOwner) { data ->
                        when (data) {
                            is UiState.Loading -> {
                                indeterminateMode = true
                            }
                            is UiState.Success -> {
                                indeterminateMode = false
                                val sum = data.data.mapNotNull { it.toDoubleOrNull() }.sum()
                                progress = sum.toFloat()
                                binding.waterProgressText.text =
                                    resources.getString(
                                        R.string.waters_count,
                                        progress.toInt().toString(),
                                        Helper.roundFloat(progressMax, 1).toString()
                                    )
                            }
                            is UiState.Error -> {
                                indeterminateMode = false

                            }
                        }
                    }
            }
        }

        viewModel.getAllDaily().observe(viewLifecycleOwner) { data ->
            when (data) {
                is UiState.Loading -> {
                }
                is UiState.Success -> {
                    val listData = data.data
                    listAdapter.submitList(listData)
                }
                is UiState.Error -> {
                    Toast.makeText(requireContext(), data.error, Toast.LENGTH_SHORT).show()
                    Log.e("error hadeh calori", data.error)
                }
            }
        }

        viewModel.getAllDailyWorkout().observe(viewLifecycleOwner) { data ->
            when (data) {
                is UiState.Loading -> {
                }
                is UiState.Success -> {
                    val listData = data.data
                    listAdapterWorkout.submitList(listData)
                }
                is UiState.Error -> {
                    Toast.makeText(requireContext(), data.error, Toast.LENGTH_SHORT).show()
                    Log.e("error hadeh workout", data.error)
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = FoodWaterHistoryAdapter()

        listAdapterWorkout = WorkoutHistoryAdapter()

        binding.dashboardClock.format12Hour = "hh:mm a"

        viewModel.getUserData("username").observe(viewLifecycleOwner) { username ->
            binding.usernameDashboard.text = username
        }

        binding.miniRvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }

        binding.miniRvHistoryWorkout.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapterWorkout
        }

        binding.caloriesCardview.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_caloriesFragment)
        }

        binding.waterCardview.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_caloriesFragment)
        }

        binding.waterFoodAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_dairyIntakeFragment)
        }

        binding.workoutActionAddButton.setOnClickListener {
            viewModel.getAllWorkout().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        workoutData = result.data
                        val action =
                            DashboardFragmentDirections.actionNavigationHomeToWorkoutIntakeFragment(
                                (workoutData as List<WorkoutEntity>).toTypedArray()
                            )
                        findNavController().navigate(action)
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}