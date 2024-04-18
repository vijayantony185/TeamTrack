package com.example.teamtrack.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.teamtrack.R
import com.example.teamtrack.arch.Data
import com.example.teamtrack.arch.localDB.UserDAO
import com.example.teamtrack.databinding.ActivityPieChartBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PieChartActivity : AppCompatActivity() {

    lateinit var binding: ActivityPieChartBinding
    @Inject lateinit var userDAO  : UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@PieChartActivity, R.layout.activity_pie_chart)
        binding.toolbar.apply {
            setSupportActionBar(this)
            setTitleTextColor(ContextCompat.getColor(this@PieChartActivity, R.color.white))
            setNavigationOnClickListener {
                finish()
            }
        }

        userDAO.getAllUsers().observe(this@PieChartActivity) {
            setPieChart(getCountryCounts(ArrayList(it)))
        }

    }

    private fun setPieChart(countryBaseUsers : Map<String, Int>){
        val pie = AnyChart.pie()
        val dataEntries = arrayListOf<DataEntry>()

        for ((country, count) in countryBaseUsers) {
            dataEntries.add(ValueDataEntry(country, count))
        }
        pie.data(dataEntries)
        binding.pieChart.setChart(pie)
    }

    private fun getCountryCounts(dataList: List<Data>): Map<String, Int> {
        val countryCounts = HashMap<String, Int>()

        for (data in dataList) {
            val country = data.country ?: "Unknown"
            val count = countryCounts.getOrDefault(country, 0)
            countryCounts[country] = count + 1
        }

        return countryCounts
    }
}