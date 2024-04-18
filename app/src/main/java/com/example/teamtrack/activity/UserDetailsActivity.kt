package com.example.teamtrack.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.teamtrack.R
import com.example.teamtrack.arch.getSingleUserInfo.SingleUserViewModel
import com.example.teamtrack.databinding.ActivityUserDetailsBinding
import com.example.teamtrack.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private val viewModel : SingleUserViewModel by viewModels()
    private lateinit var binding : ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val userId = intent.getIntExtra(Constants.USER_ID, 0)
        if (userId > 0) {
            viewModel.getSingleUser(userId)
        }

        viewModel.userData.observe(this) { user ->
            val username = "${user.firstName} ${user.lastName}"
            Glide.with(this).load(user.avatar).placeholder(R.mipmap.ic_launcher).into(binding.ivUserImage)
            binding.apply {
                tvUserName.text = username
                toolbar.title = username
                tvUserEmail.text = user.email
            }
        }
    }
}