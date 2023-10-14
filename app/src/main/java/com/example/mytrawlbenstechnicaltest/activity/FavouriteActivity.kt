package com.example.mytrawlbenstechnicaltest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytrawlbenstechnicaltest.adapter.CommentAdapter
import com.example.mytrawlbenstechnicaltest.databinding.ActivityFavouriteBinding
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.viewmodel.FavouriteViewModel
import java.util.Locale

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var searchList: ArrayList<CommentEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        var toolbar = binding.myToolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

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

//            binding.search.clearFocus()
//            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    binding.search.clearFocus()
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    searchList.clear()
//    //                    adapter.notifyDataSetChanged()
//                    val searchText = newText!!.toLowerCase(Locale.getDefault())
//                    if (searchText.isNotEmpty()){
//                        searchList.clear()
//    //                        adapter.notifyDataSetChanged()
//                        dataKomen?.forEach{
//                            if (it.email?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
//                                searchList.add(it)
//    //                                adapter.notifyDataSetChanged()
//                            }
//                        }
//                        binding.recyclerView.adapter!!.notifyDataSetChanged()
//                    } else {
//                        searchList.clear()
//    //                        adapter.notifyDataSetChanged()
//                        if (dataKomen != null) {
//                            searchList.addAll(dataKomen)
//    //                            adapter.notifyDataSetChanged()
//                        }
//                        binding.recyclerView.adapter!!.notifyDataSetChanged()
//                    }
//                    return false
//                }
//            })
            adapter.setListItem(searchList)
            showLoading(false)

            adapter.setOnItemClickListener(object : CommentAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    //                    Toast.makeText(this@MainActivity, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@FavouriteActivity, "$position, ${searchList[position].id}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@FavouriteActivity, DataDetailActivity::class.java)
                    intent.putExtra("id", searchList[position].id)
                    startActivity(intent)
                }
            })
        }



    }

//    private fun fetchData() {
//        lifecycleScope.launchWhenCreated {
//            launch {
//                viewModel.fetchData()
//                showLoading(true)
//            }
//        }
//    }

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