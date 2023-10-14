package com.example.mytrawlbenstechnicaltest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytrawlbenstechnicaltest.R
import com.example.mytrawlbenstechnicaltest.adapter.DataAdapter
import com.example.mytrawlbenstechnicaltest.databinding.ActivityMainBinding
import com.example.mytrawlbenstechnicaltest.model.DataItem
import com.example.mytrawlbenstechnicaltest.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var searchList: ArrayList<DataItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        var toolbar = binding.myToolbar
        toolbar.inflateMenu(R.menu.top_nav_bar_menu)
        toolbar.title = "Trawlbens Android"
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)

        fetchData()
        searchList = arrayListOf<DataItem>()

        viewModel.dataLiveData.observe(this) { dataKomen ->
            searchList.addAll(dataKomen)

            binding.search.clearFocus()
            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.search.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchList.clear()
                    val searchText = newText!!.toLowerCase(Locale.getDefault())
                    if (searchText.isNotEmpty()){
                        searchList.clear()
                        dataKomen.forEach{
                            if (it.email?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                                searchList.add(it)
                            }
                        }
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                    } else {
                        searchList.clear()
                        searchList.addAll(dataKomen)
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return false
                }
            })

            val adapter = DataAdapter(searchList)
            val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            adapter.notifyDataSetChanged()
            binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            binding.recyclerView.adapter = adapter

            showLoading(false)

            adapter.setOnItemClickListener(object : DataAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
//                    Toast.makeText(this@MainActivity, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@MainActivity, "${searchList[position].id}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, DataDetailActivity::class.java)
                    intent.putExtra("id", searchList[position].id)
                    startActivity(intent)
                }
            })



        }
    }

    private fun fetchData() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.fetchData()
                showLoading(true)
            }
        }
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_nav_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                fetchData()

                true
            }
            R.id.favourite -> {
//                val intent = Intent(this@SensorActivity, AddVMActivity::class.java)
//                startActivity(intent)
//                true
                Toast.makeText(
                    applicationContext,
                    "Masih dalam tahap pengembangan.",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            else -> {
                true
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}