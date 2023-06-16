package com.bangkit.bangkitcapstone.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentWorkoutDetailBinding
import com.bangkit.bangkitcapstone.model.data.local.entity.WorkoutEntity


class WorkoutDetailFragment : Fragment() {

    private var _binding: FragmentWorkoutDetailBinding? = null
    private val binding get() = _binding!!
    private var data: WorkoutEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            data = WorkoutDetailFragmentArgs.fromBundle(it).workoutEnity
            setupUi(data!!)
        }

        binding.workoutDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.workoutButton.setOnClickListener {
            val name = data?.name ?: ""
            val met = data?.cal ?: ""
            val action = WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToTimerFragment(
                name,
                met
            )
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi(data: WorkoutEntity) {
        binding.workoutNameDetail.text = data.name
        binding.workoutDesc.text = data.desc
        binding.workoutType.text = data.type
        binding.workoutBodypart.text = data.bodyPart
        binding.workoutLevel.text = data.level
    }
}
