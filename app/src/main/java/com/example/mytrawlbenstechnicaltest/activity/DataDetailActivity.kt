package com.example.mytrawlbenstechnicaltest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mytrawlbenstechnicaltest.R
import com.example.mytrawlbenstechnicaltest.databinding.ActivityDataDetailBinding
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.viewmodel.DataDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataDetailBinding
    private lateinit var viewModel: DataDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DataDetailViewModel::class.java]

        val bundle : Bundle?= intent.extras
        val id = bundle!!.getInt("id")

        hideActionBar()
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var toolbar = binding.myToolbar
//        toolbar.inflateMenu(R.menu.detail_top_nav_bar_menu)
        toolbar.title = ""
//        setSupportActionBar()

        fetchData(id)

        binding.back.setOnClickListener {
            onSupportNavigateUp()
        }


        viewModel.dataLiveData.observe(this) {data ->
//            if (checkFavourite(data.id!!)) {
//                Toast.makeText(this, "sudah favourite", Toast.LENGTH_SHORT).show()
//                binding.favourite.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24_red))
//            } else {
//                Toast.makeText(this, "belum favourite", Toast.LENGTH_SHORT).show()
//                binding.favourite.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24))
//            }
            var _isCheked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUser(id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            binding?.favourite?.isChecked = true
                            _isCheked = true
                        } else {
                            binding?.favourite?.isChecked = false
                            _isCheked = false
                        }
                    }
                }
            }
            binding?.favourite?.setOnClickListener {
                _isCheked = !_isCheked
                if (_isCheked) {
                    if (data != null) {
                        viewModel.insert(data.name, data.postId, data.id, data.body, data.email)
                    }
                } else {
                    viewModel.removeFromFavorite(id)
                }

                binding?.favourite?.isChecked = _isCheked
            }

//            binding.favourite.setOnClickListener {
//                if (checkFavourite(data.id!!)) {
//                    deleteFavourite(CommentEntity(data.name, data.postId, data.id, data.body, data.email))
//                    Toast.makeText(this, "dihapus favourite", Toast.LENGTH_SHORT).show()
////                    binding.favourite.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24_red))
//                } else {
//                    viewModel.insert(data.name, data.postId, data.id, data.body, data.email)
//                    Toast.makeText(this, "ditambahkan favourite", Toast.LENGTH_SHORT).show()
//                }
//
//            }

            binding.title.text = data.email.toString()
            binding.tvName.text = data.name.toString()
            binding.tvPostId.text = data.postId.toString()
            binding.tvId.text = data.id.toString()
            binding.tvBody.text = data.body.toString()
            binding.tvEmail.text = data.email.toString()
            showLoading(false)
        }
    }

    private fun fetchData(id: Int) {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.fetchData(id)
                showLoading(true)
            }
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

//    private fun addFavourite(data: CommentEntity) {
//       viewModel.insert(data)
//    }

//    private fun deleteFavourite(data: CommentEntity) {
//        viewModel.delete(data)
//    }
//
//    private fun checkFavourite(id: Int) :Boolean {
//        if (viewModel.getAllNotes().value.isNullOrEmpty()) {
//            return false
//        } else {
//            for (i in viewModel.getAllNotes().value!!) {
//                return id == i.id
//            }
//        }
//       return false
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.detail_top_nav_bar_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.favourite -> {
////                val intent = Intent(this@SensorActivity, AddVMActivity::class.java)
////                startActivity(intent)
////                true
//                Toast.makeText(
//                    applicationContext,
//                    "Masih dalam tahap pengembangan.",
//                    Toast.LENGTH_LONG
//                ).show()
//                true
//            }
//            else -> {
//                true
//            }
//        }
//    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}