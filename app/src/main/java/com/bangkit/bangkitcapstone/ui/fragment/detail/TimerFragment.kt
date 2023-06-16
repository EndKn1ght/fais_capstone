package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentTimerBinding
import com.bangkit.bangkitcapstone.helper.Helper.convertTimeStringToSeconds
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.WorkoutViewModel

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private var dataName: String = ""
    private var met: String = ""
    private val viewModel: WorkoutViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dataName = TimerFragmentArgs.fromBundle(it).name.toString()
            met = TimerFragmentArgs.fromBundle(it).met.toString()
        }

        stopWatch()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun stopWatch() {
        var startTime: Long = 0
        var isRunning = false

        binding.startButton.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime()
                binding.stopwatchTextView.base = startTime
                binding.stopwatchTextView.start()
                isRunning = true

                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.timer_start),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.stopButton.setOnClickListener {
            if (isRunning) {
                binding.stopwatchTextView.stop()
                isRunning = false
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.timer_stop),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.resetButton.setOnClickListener {
            binding.stopwatchTextView.stop()
            binding.stopwatchTextView.base = SystemClock.elapsedRealtime()
            isRunning = false
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.timer_reset),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.caluculateButton.setOnClickListener {
            val data = binding.stopwatchTextView.text
            val totalSeconds = convertTimeStringToSeconds(data.toString())
            val timeData = totalSeconds / 3600f

            viewModel.getBmr().observe(viewLifecycleOwner) {
                val bmr = if (it.isNullOrEmpty()) 0f else it.toFloat()
                val intake = (bmr / 24.0) * timeData * met.toFloat()
                viewModel.insertWorkout(dataName, intake.toString(), timeData.toString())
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            }
                            is UiState.Error -> {
                                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }
            }
        }

    }
}