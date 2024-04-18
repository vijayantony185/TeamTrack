package com.example.teamtrack.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamtrack.R
import com.example.teamtrack.adapter.ItemListener
import com.example.teamtrack.adapter.UserPagingAdapter
import com.example.teamtrack.arch.Data
import com.example.teamtrack.arch.getAllUsers.UserDetailsViewModel
import com.example.teamtrack.arch.localDB.UserDAO
import com.example.teamtrack.arch.localDB.UserSessionManager
import com.example.teamtrack.databinding.ActivityHomeBinding
import com.example.teamtrack.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val userViewModel: UserDetailsViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userPagingAdapter: UserPagingAdapter

    @Inject
    lateinit var userDAO: UserDAO

    @Inject
    lateinit var userSessionManager: UserSessionManager
    private val items = arrayOf(
        "Show All Countries", "China", "India", "Indonesia", "Pakistan", "Bangladesh",
        "Japan", "Philippines", "Vietnam"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.toolbar.apply {
            setSupportActionBar(this)
            setTitleTextColor(ContextCompat.getColor(this@HomeActivity, R.color.white))
            title = ContextCompat.getString(this@HomeActivity, R.string.app_name)
        }

        userPagingAdapter = UserPagingAdapter(this, object : ItemListener {
            override fun item(data: Data) {
                data.id?.let { userId ->
                    val intent = Intent(this@HomeActivity, UserDetailsActivity::class.java)
                    intent.putExtra(Constants.USER_ID, userId)
                    startActivity(intent)
                }
            }
        })
        binding.rcvUsers.layoutManager = LinearLayoutManager(this)
        binding.rcvUsers.adapter = userPagingAdapter

        lifecycleScope.launch {
            try {
                userViewModel.getUsers().collectLatest { pagingData ->
                    userPagingAdapter.submitData(pagingData)
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

        binding.fabFilter.setOnClickListener {
            showCountryFilter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_chart -> {
                val intent = Intent(this@HomeActivity, PieChartActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_logout -> {
                showLogoutAlert()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCountryFilter() {
        AlertDialog.Builder(this).apply {
            setTitle(ContextCompat.getString(this@HomeActivity, R.string.select_country))

            setItems(items) { dialog, which ->
                val selectedItem = items[which]
                userDAO.getAllUsers().observe(this@HomeActivity) { data ->
                    val filteredList = ArrayList(data).filter { it.country == selectedItem }
                    lifecycleScope.launch {
                        try {
                            val pagingData: PagingData<Data> =
                                if (selectedItem == ContextCompat.getString(
                                        this@HomeActivity,
                                        R.string.showAllUsers
                                    )
                                ) {
                                    PagingData.from(ArrayList(data))
                                } else {
                                    PagingData.from(filteredList)
                                }
                            userPagingAdapter.submitData(pagingData)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                dialog.dismiss()
            }
            this.create().show()
        }
    }

    private fun showLogoutAlert() {
        AlertDialog.Builder(this).apply {
            setTitle(ContextCompat.getString(this@HomeActivity, R.string.logout))
            setMessage(ContextCompat.getString(this@HomeActivity, R.string.exit_message))
            setPositiveButton(
                ContextCompat.getString(
                    this@HomeActivity,
                    R.string.yes
                )
            ) { dialog, _ ->
                userSessionManager.clearUserSession()
                dialog.dismiss()
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            setNegativeButton(
                ContextCompat.getString(
                    this@HomeActivity,
                    R.string.no
                )
            ) { dialog, _ ->
                dialog.dismiss()
            }
            this.create().show()
        }
    }
}