package com.bangkit.bangkitcapstone.ui.fragment.home

import android.os.Bundle
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
import com.bangkit.bangkitcapstone.databinding.FragmentRecipeBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.adapter.RecipeListAdapter
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.RecipesViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipesViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

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
            val action = RecipeFragmentDirections.actionNavigationRecipeToFoodDetailFragment(it)
            findNavController().navigate(action)
        }

        viewModel.getRecipeList().observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBarRecipeList.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.progressBarRecipeList.visibility = View.INVISIBLE
                    val response = result.data
                    listAdapter.submitList(response)
                }
                is UiState.Error -> {
                    binding.progressBarRecipeList.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        binding.rvListFood.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }


        binding.searchBarRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}