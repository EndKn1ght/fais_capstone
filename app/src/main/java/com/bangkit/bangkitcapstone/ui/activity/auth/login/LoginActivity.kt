package com.bangkit.bangkitcapstone.ui.activity.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.ActivityLoginBinding
import com.bangkit.bangkitcapstone.ui.activity.auth.home.HomeActivity
import com.bangkit.bangkitcapstone.ui.activity.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}