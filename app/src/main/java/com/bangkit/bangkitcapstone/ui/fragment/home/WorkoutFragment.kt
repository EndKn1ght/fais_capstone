package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentWorkoutBinding
import com.bangkit.bangkitcapstone.model.data.remote.response.DummyData
import com.bangkit.bangkitcapstone.ui.adapter.WorkoutListAdapter

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = WorkoutListAdapter {
            Toast.makeText(
                requireContext(),
                "This Feature not implemented yet!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.workoutRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }

        listAdapter.submitList(DummyData.workout)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}