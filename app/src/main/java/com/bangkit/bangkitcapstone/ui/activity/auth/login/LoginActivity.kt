package com.bangkit.bangkitcapstone.ui.activity.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bangkitcapstone.databinding.ActivityLoginBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.activity.auth.home.HomeActivity
import com.bangkit.bangkitcapstone.ui.activity.auth.register.RegisterActivity
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.AuthViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToRegisterPage()
        login()
    }

    private fun goToRegisterPage() {
        binding.registerTextButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val username = binding.usernameLoginInput.text
        val password = binding.passwordLoginInput.text

        binding.loginButton.setOnClickListener {

            viewModel.loginAuth(username.toString(), password.toString()).observe(this) { result ->
                when (result) {
                    is UiState.Loading -> {
                        binding.progressBarLogin.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        val token = result.data.accessToken
                        viewModel.saveUserData(
                            mapOf(
                                "token" to "$token",
                            )
                        ).observe(this) {
                            when (it) {
                                is UiState.Loading -> {
                                }
                                is UiState.Success -> {
                                    viewModel.getUserDetail(token.toString())
                                        .observe(this) { detail ->
                                            when (detail) {
                                                is UiState.Loading -> {}
                                                is UiState.Success -> {
                                                    viewModel.saveUserData(
                                                        mapOf(
                                                            "username" to "${detail.data.username}",
                                                            "email" to "${detail.data.email}"
                                                        )
                                                    ).observe(this) { data ->
                                                        when (data) {
                                                            is UiState.Loading -> {
                                                            }
                                                            is UiState.Success -> {
                                                                binding.progressBarLogin.visibility =
                                                                    View.INVISIBLE
                                                                val intent = Intent(
                                                                    this,
                                                                    HomeActivity::class.java
                                                                )
                                                                startActivity(intent)
                                                                finish()
                                                            }
                                                            is UiState.Error -> {
                                                            }
                                                            else -> {}
                                                        }
                                                    }
                                                }
                                                is UiState.Error -> {}
                                                else -> {}
                                            }
                                        }
                                }
                                is UiState.Error -> {
                                    binding.progressBarLogin.visibility = View.INVISIBLE
                                }
                                else -> {}
                            }
                        }
                    }
                    is UiState.Error -> {
                        binding.progressBarLogin.visibility = View.INVISIBLE
                        Snackbar.make(binding.root, result.error, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }


}