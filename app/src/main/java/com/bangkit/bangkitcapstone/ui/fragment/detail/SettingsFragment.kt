package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.databinding.FragmentSettingsBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.SettingsViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButtonSettings.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getToken("token").observe(viewLifecycleOwner) { tokenData ->
            token = tokenData
        }

        binding.deleteHistory.setOnClickListener {
            viewModel.deleteHistory().observe(viewLifecycleOwner) { delete ->
                when (delete) {
                    is UiState.Loading -> {
                        binding.progressBarSettings.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                        Toast.makeText(
                            requireActivity(),
                            "Success Delete All History",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is UiState.Error -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), delete.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        binding.uploadHistory.setOnClickListener {
            binding.progressBarSettings.visibility = View.VISIBLE
            viewModel.getAllCaloriesIntake().observe(viewLifecycleOwner) { dailyIntake ->
                when (dailyIntake) {
                    is UiState.Loading -> {
                        binding.progressBarSettings.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                        val data = dailyIntake.data
                        data.forEach { item ->
                            viewModel.uploadCaloriesIntake(
                                token,
                                item.dateIntake,
                                item.name,
                                item.intake.toFloat()
                            ).observe(viewLifecycleOwner) { response ->
                                when (response) {
                                    is UiState.Loading -> {}
                                    is UiState.Success -> {
                                        Toast.makeText(
                                            requireContext(),
                                            "Success Upload",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    is UiState.Error -> {
                                        Toast.makeText(
                                            requireContext(),
                                            response.error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), dailyIntake.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                    }
                }
            }

            viewModel.getAllWorkout().observe(viewLifecycleOwner) { workoutIntake ->
                when (workoutIntake) {
                    is UiState.Loading -> {
                        binding.progressBarSettings.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                        val data = workoutIntake.data
                        data.forEach { item ->
                            item.duration?.let { it1 ->
                                viewModel.uploadWorkoutIntake(
                                    token,
                                    (item.id - 1).toString(),
                                    item.dateIntake,
                                    it1.toFloat(),
                                    item.intake.toFloat()
                                ).observe(viewLifecycleOwner) { response ->
                                    when (response) {
                                        is UiState.Loading -> {}
                                        is UiState.Success -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Success Upload",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        is UiState.Error -> {
                                        }
                                        else -> {}
                                    }
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        binding.progressBarSettings.visibility = View.INVISIBLE
                    }
                    else -> {}
                }
            }
        }

        binding.downloadHistory.setOnClickListener {
            viewModel.downloadHistory(token).observe(viewLifecycleOwner) { download ->
                when (download) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        viewModel.deleteHistory().observe(viewLifecycleOwner) { delete ->
                            when (delete) {
                                is UiState.Loading -> {
                                    binding.progressBarSettings.visibility = View.VISIBLE
                                }
                                is UiState.Success -> {
                                    binding.progressBarSettings.visibility = View.INVISIBLE
                                    val downloadData = download.data
                                    viewModel.insertIntake(downloadData).observe(viewLifecycleOwner) {
                                        when (it) {
                                            is UiState.Loading -> {}
                                            is UiState.Success -> {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Success Download Data",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            is UiState.Error -> {
                                                Log.e("banyak error", it.error)
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                                is UiState.Error -> {
                                    binding.progressBarSettings.visibility = View.INVISIBLE
                                    Toast.makeText(
                                        requireContext(),
                                        delete.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.e("HADE", delete.error)
                                }
                                else -> {}
                            }
                        }
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), download.error, Toast.LENGTH_SHORT).show()
                        Log.e("Dweonladasdf", download.error)
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