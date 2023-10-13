package com.example.mytrawlbenstechnicaltest.activity

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.mytrawlbenstechnicaltest.R
import com.example.mytrawlbenstechnicaltest.databinding.ActivityDataDetailBinding
import com.example.mytrawlbenstechnicaltest.viewmodel.DataDetailViewModel

class DataDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataDetailBinding
    private lateinit var viewModel: DataDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DataDetailViewModel::class.java]

        val bundle : Bundle?= intent.extras
        val detail = bundle!!.getString("detail")


//        hideActionBar()
//        var toolbar = binding.myToolbar
//        toolbar.inflateMenu(R.menu.top_nav_bar_menu)
//        toolbar.title = ""
//        setSupportActionBar()

        binding.back.setOnClickListener {
            onSupportNavigateUp()
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

//    private fun showLoading(state: Boolean) {
//        if (state) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }

}