package com.bangkit.bangkitcapstone.ui.activity.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.ActivityRegisterBinding
import com.bangkit.bangkitcapstone.ui.activity.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToLoginPage()
    }

    private fun goToLoginPage() {
        binding.loginTextButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}