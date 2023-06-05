package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentRecipeBinding
import com.bangkit.bangkitcapstone.model.data.remote.response.RecipeDummy
import com.bangkit.bangkitcapstone.ui.adapter.RecipeListAdapter

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = RecipeListAdapter {
            findNavController().navigate(R.id.action_navigation_recipe_to_foodDetailFragment)
        }

        binding.rvListFood.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }

        listAdapter.submitList(RecipeDummy.food)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}