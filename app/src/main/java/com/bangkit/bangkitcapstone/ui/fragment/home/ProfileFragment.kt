package com.bangkit.bangkitcapstone.ui.fragment.home

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.FragmentProfileBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.activity.auth.login.LoginActivity
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ProfileViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserData("username").observe(viewLifecycleOwner) { username ->
            binding.userRealname.text = username
        }

        viewModel.getUserData("email").observe(viewLifecycleOwner) { email ->
            binding.userEmail.text = email
        }

        binding.optionSettings.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }

        binding.logoutProfile.setOnClickListener {
            alertDialog()
        }

        viewModel.getThemeSettings().observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                changeDarkIcon(it)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                changeDarkIcon(it)
            }
        }

        setDarkMode()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.alert_title))
        builder.setMessage(resources.getString(R.string.alert_message))
        builder.setPositiveButton(resources.getString(R.string.alert_confirm)) { _, _ ->
            viewModel.deleteToken(mapOf("token" to "")).observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        logOut()
                    }
                    is UiState.Error -> {}
                    else -> {}
                }
            }
        }
        builder.setNegativeButton(resources.getString(R.string.alert_cancel)) { _, _ -> }
        builder.show()
    }

    private fun logOut() {
        val intent = Intent(activity, LoginActivity::class.java)
        if (activity != null) {
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setDarkMode() {
        val switchTheme = binding.darkMode
        switchTheme.setOnClickListener {
            val isDarkMode =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            if (isDarkMode) {
                viewModel.setTheme(false)
            } else {
                viewModel.setTheme(true)
            }
        }
    }

    private fun changeDarkIcon(state: Boolean) {
        val textView = binding.darkModeTv
        val drawableStartLight =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_light_mode)
        val drawableStartDark = ContextCompat.getDrawable(requireContext(), R.drawable.ic_dark_mode)
        val drawablEnd = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_right)
        if (state) {
            textView.setCompoundDrawablesWithIntrinsicBounds(
                drawableStartLight,
                null,
                drawablEnd,
                null
            )
            textView.text = resources.getString(R.string.lightmode)
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(
                drawableStartDark,
                null,
                drawablEnd,
                null
            )
            textView.text = resources.getString(R.string.darkmode)
        }
    }
}