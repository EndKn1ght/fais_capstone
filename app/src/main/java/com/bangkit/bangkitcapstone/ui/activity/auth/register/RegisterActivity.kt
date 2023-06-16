package com.bangkit.bangkitcapstone.ui.activity.auth.register

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bangkitcapstone.databinding.ActivityRegisterBinding
import com.bangkit.bangkitcapstone.model.data.UiState
import com.bangkit.bangkitcapstone.ui.activity.auth.login.LoginActivity
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.AuthViewModel
import com.bangkit.bangkitcapstone.ui.fragment.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                binding.dateInput.text = Editable.Factory.getInstance().newEditable(selectedDate)
            }

        binding.calendarButton.setOnClickListener {
            showDatePickerDialog(this, onDateSetListener)
        }
        setupUi()
    }

    private fun setupUi() {
        val username = binding.usernameRegisterInput.text
        val password = binding.passwordRegisterInput.text
        val email = binding.emailRegisterInput.text
        var date = binding.dateInput.text
        val height = binding.heightInput.text
        val weight = binding.weightInput.text
        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                date = Editable.Factory.getInstance().newEditable(selectedDate)
            }

        binding.calendarButton.setOnClickListener {
            showDatePickerDialog(this, onDateSetListener)
        }

        binding.registerButton.setOnClickListener {
            val newHeight = if (height.isNullOrEmpty()) "0" else height
            val newWeight = if (weight.isNullOrEmpty()) "0" else weight

            viewModel.registerAuth(
                username.toString(),
                password.toString(),
                email.toString(),
                date.toString(),
                newHeight.toString().toInt(),
                newWeight.toString().toInt()
            ).observe(this) { result ->
                when (result) {
                    is UiState.Loading -> {
                        binding.progressBarRegister.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBarRegister.visibility = View.INVISIBLE
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is UiState.Error -> {
                        binding.progressBarRegister.visibility = View.INVISIBLE
                        Snackbar.make(binding.root, result.error, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        binding.loginTextButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDatePickerDialog(
        context: Context,
        onDateSetListener: DatePickerDialog.OnDateSetListener
    ) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, onDateSetListener, year, month, dayOfMonth)
        datePickerDialog.show()
    }

}