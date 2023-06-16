package com.bangkit.bangkitcapstone.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.bangkitcapstone.databinding.ActivityMainBinding
import com.bangkit.bangkitcapstone.ui.activity.auth.home.HomeActivity
import com.bangkit.bangkitcapstone.ui.activity.auth.login.LoginActivity
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: SplashViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        viewModel.getToken(TOKEN).observe(this) {
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                if (it.isNullOrEmpty()) {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000L)
        }
    }

    companion object {
        private const val TOKEN = "token"
    }
}