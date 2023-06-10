package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentCaloriesBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.CaloriesViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CaloriesFragment : Fragment() {

    private var _binding: FragmentCaloriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CaloriesViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaloriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val activities = resources.getStringArray(R.array.activity)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_item_activities, activities)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.caloriesBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.caloriesButton.setOnClickListener {
            val weightInput = binding.weightInput.text.toString().trim()
            val heightInput = binding.heightInput.text.toString().trim()
            val ageInput = binding.ageInput.text.toString().trim()
            val activity = binding.autoCompleteTextView.text.toString().trim()
            val gender = when (binding.caloriesRadioGroup.checkedRadioButtonId) {
                R.id.male_radio_button -> true
                R.id.female_radio_button -> false
                else -> true
            }

            if (weightInput.isNotEmpty() && heightInput.isNotEmpty() && ageInput.isNotEmpty()) {
                val weight = weightInput.toInt()
                val height = heightInput.toInt()
                val age = ageInput.toInt()

                val bmr = viewModel.bmrCalculator(weight, height, age, gender, activity)
                viewModel.saveUserData(
                    mapOf(
                        "bmr" to "$bmr",
                        "height" to "$height",
                        "weight" to "$weight",
                        "age" to "$age",
                        "activity" to activity,
                    )
                )
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                findNavController().popBackStack()
                            }
                            is UiState.Error -> {
                                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
            } else {
                Snackbar.make(requireView(), "Please Input All The Data", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getAllUserData().observe(viewLifecycleOwner) { dataMap ->
            binding.weightInput.text = Editable.Factory.getInstance().newEditable(dataMap["weight"])
                ?: Editable.Factory.getInstance().newEditable("")
            binding.heightInput.text = Editable.Factory.getInstance().newEditable(dataMap["height"])
                ?: Editable.Factory.getInstance().newEditable("")
            binding.ageInput.text = Editable.Factory.getInstance().newEditable(dataMap["age"])
                ?: Editable.Factory.getInstance().newEditable("")
            binding.autoCompleteTextView.text = Editable.Factory.getInstance().newEditable(dataMap["activity"])
                ?: Editable.Factory.getInstance().newEditable("Options")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}