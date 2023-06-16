package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentDairyIntakeBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.model.data.local.IntakeType
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.CalculatorViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class DairyIntakeFragment : Fragment() {

    private var _binding: FragmentDairyIntakeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var typeIntake = IntakeType.FOOD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDairyIntakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.intakeBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.addIntakeButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val carbs = binding.carbInput.text.toString()
            val prot = binding.protInput.text.toString()
            val fat = binding.fatInput.text.toString()
            val water = binding.calInput.text.toString()
            if (typeIntake == IntakeType.WATER) {
                viewModel.wantersIntake(water).observe(viewLifecycleOwner) {
                    when (it) {
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else if (typeIntake == IntakeType.FOOD) {
                viewModel.caloriesIntake(
                    name, carbs.toFloat(), prot.toFloat(), fat.toFloat()
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val autoComplete = binding.autoCompleteOption
        val options = resources.getStringArray(R.array.option)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_item_activities, options)
        autoComplete.setAdapter(arrayAdapter)

        autoComplete.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = options[position]
            if (selectedOption == "Food") {
                typeIntake = IntakeType.FOOD
                setToggleUi(true)
            } else if (selectedOption == "Water") {
                typeIntake = IntakeType.WATER
                setToggleUi(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setToggleUi(state: Boolean) {
        val views = arrayOf(
            binding.carbInput,
            binding.carbTv,
            binding.protTv,
            binding.protInput,
            binding.fatTv,
            binding.fatInput,
            binding.nameTv,
            binding.nameInput,
        )

        val option = arrayOf(
            binding.calInput, binding.calTv
        )

        if (state) {
            for (view in views) {
                view.isEnabled = true
                view.alpha = 1f
            }
            for (opt in option) {
                opt.isEnabled = false
                opt.alpha = 0.5f
            }

        } else {
            for (view in views) {
                view.isEnabled = false
                view.alpha = 0.5f
            }
            for (opt in option) {
                opt.isEnabled = true
                opt.alpha = 1f
            }
        }
    }

}