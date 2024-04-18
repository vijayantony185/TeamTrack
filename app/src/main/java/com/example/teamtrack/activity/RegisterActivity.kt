package com.example.teamtrack.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.teamtrack.R
import com.example.teamtrack.arch.userRegister.RegisterRequest
import com.example.teamtrack.arch.userRegister.RegisterViewModel
import com.example.teamtrack.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@RegisterActivity, R.layout.activity_register)
        onClicks()
        viewModel.RegisterResponse.observe(this) {
            if (it.isSuccessful) {
                Toast.makeText(
                    this@RegisterActivity,
                    ContextCompat.getString(this@RegisterActivity, R.string.register_success),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val temp = it.message().ifEmpty {
                    ContextCompat.getString(
                        this@RegisterActivity,
                        R.string.register_failed
                    )
                }
                Toast.makeText(this@RegisterActivity, temp, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClicks() {
        binding.btnRegister.setOnClickListener {
            validation()
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validation() {
        binding.apply {
            val userMail = edtUserEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()
            if (userMail.isEmpty()) {
                edtUserEmail.error = getString(R.string.error_message_usermail_is_empty)
                return
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
                edtUserEmail.error = getString(R.string.error_message_usermail_not_valid)
                return
            } else if (password.isEmpty()) {
                edtPassword.error = getString(R.string.error_message_password_empty)
                return
            } else if (password.length < 6) {
                edtPassword.error = getString(R.string.error_message_password_character)
                return
            } else if (confirmPassword.isEmpty()) {
                edtConfirmPassword.error = getString(R.string.error_message_confirm_password_empty)
                return
            } else if (password != confirmPassword) {
                edtConfirmPassword.error = getString(R.string.password_mismatch)
                return
            } else {
                viewModel.registerUser(RegisterRequest(userMail, password))
            }
        }
    }
}