package com.example.mytrawlbenstechnicaltest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytrawlbenstechnicaltest.adapter.CommentAdapter
import com.example.mytrawlbenstechnicaltest.databinding.ActivityCommentFavouriteBinding
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.viewmodel.CommentFavouriteViewModel

class CommentFavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentFavouriteBinding
    private lateinit var viewModel: CommentFavouriteViewModel
    private lateinit var searchList: ArrayList<CommentEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        var toolbar = binding.myToolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)[CommentFavouriteViewModel::class.java]

        binding.recyclerView.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)

        searchList = arrayListOf<CommentEntity>()

        binding.back.setOnClickListener {
            binding?.back?.isChecked = true
            onSupportNavigateUp()
        }

        val adapter = CommentAdapter()
        val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        adapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
        binding.recyclerView.adapter = adapter


        viewModel.fetchData()?.observe(this) { dataKomen ->
            searchList.clear()
            if (dataKomen != null) {
                searchList.addAll(dataKomen)
                adapter.notifyDataSetChanged()
            }

            adapter.setListItem(searchList)
            showLoading(false)

            adapter.setOnItemClickListener(object : CommentAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    val intent = Intent(this@CommentFavouriteActivity, CommentDetailActivity::class.java)
                    intent.putExtra("id", searchList[position].id)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}