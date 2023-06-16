package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentWorkoutBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.adapter.WorkoutListAdapter
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkoutViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val floatingCameraButton = binding.floatingCameraButton

        val listAdapter = WorkoutListAdapter {
            val action = WorkoutFragmentDirections.actionNavigationWorkoutToWorkoutDetailFragment(it)
            findNavController().navigate(action)
        }

        binding.workoutRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }

        viewModel.getListWorkout().observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.workoutProgressbar.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.workoutProgressbar.visibility = View.INVISIBLE
                    listAdapter.submitList(it.data)
                }
                is UiState.Error -> {
                    binding.workoutProgressbar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        binding.searchBarWorkout.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    listAdapter.resetFilter()
                } else {
                    listAdapter.filterList(newText)
                }
                return true
            }
        })

        floatingCameraButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_workout_to_cameraFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}