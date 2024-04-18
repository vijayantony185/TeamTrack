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
import com.example.teamtrack.arch.userLogIn.LoginViewModel
import com.example.teamtrack.arch.userRegister.RegisterRequest
import com.example.teamtrack.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)

        viewModel.loginResponse.observe(this){
            if (it.isSuccessful) {
                Toast.makeText(this@LoginActivity,ContextCompat.getString(this@LoginActivity, R.string.login_success),Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val temp = it.message().ifEmpty { ContextCompat.getString(this@LoginActivity, R.string.login_failed) }
                Toast.makeText(this@LoginActivity, temp,Toast.LENGTH_SHORT).show()
            }
        }

        onClicks()
    }

    private fun onClicks(){
        binding.btnLogin.setOnClickListener {
            validation()
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validation(){
        binding.apply {
            val userMail = edtUserEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            if (userMail.isEmpty()) {
                edtUserEmail.error = getString(R.string.error_message_usermail_is_empty)
                return
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
                edtUserEmail.error = getString(R.string.error_message_usermail_not_valid)
                return
            } else if (password.isEmpty()) {
                edtPassword.error =  getString(R.string.error_message_password_empty)
                return
            } else {
                viewModel.loginUser(RegisterRequest(userMail,password))
            }
        }
    }
}