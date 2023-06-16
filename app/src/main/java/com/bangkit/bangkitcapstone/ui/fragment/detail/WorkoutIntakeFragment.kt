package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentWorkoutIntakeBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.CalculatorViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class WorkoutIntakeFragment : Fragment() {

    private var _binding: FragmentWorkoutIntakeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.getAllDailyWorkout().observe(viewLifecycleOwner) {

        }

        _binding = FragmentWorkoutIntakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserData("bmr").observe(viewLifecycleOwner) { result ->
            val bmr = if (result.isNullOrEmpty()) "0" else result
            binding.workoutBmrInput.text = Editable.Factory.getInstance().newEditable(bmr)
        }

        arguments?.let {
            val data = WorkoutIntakeFragmentArgs.fromBundle(it).data
            val names: List<String> = data.map { dailyEntity -> dailyEntity.name }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_item_options, names)
            binding.wokroutAutoComplete.setAdapter(arrayAdapter)
        }

        binding.caluculateWorkout.setOnClickListener {
            val workout = binding.wokroutAutoComplete.text.toString().trim()
            val met = binding.workoutMetInput.text.toString().trim()
            val time = binding.workoutTimeInput.text.toString().trim()
            val bmr = binding.workoutBmrInput.text.toString().trim()

            if (time.isNotEmpty() && bmr.isNotEmpty() && workout.isNotEmpty()) {
                viewModel.workoutIntake(workout, bmr.toFloat(), met.toFloat(), time.toFloat())
                    .observe(viewLifecycleOwner) {
                        when (it) {
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                findNavController().popBackStack()
                                Toast.makeText(requireContext(), "Succes", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            is UiState.Error -> {
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }

        binding.wokroutIntakeBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}