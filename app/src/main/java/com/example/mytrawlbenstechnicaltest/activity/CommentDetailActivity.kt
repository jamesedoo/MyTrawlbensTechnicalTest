package com.example.mytrawlbenstechnicaltest.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mytrawlbenstechnicaltest.R
import com.example.mytrawlbenstechnicaltest.databinding.ActivityCommentDetailBinding
import com.example.mytrawlbenstechnicaltest.viewmodel.CommentDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentDetailBinding
    private lateinit var viewModel: CommentDetailViewModel

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "favourite.notifications"
    private val description = "Favourite notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CommentDetailViewModel::class.java]

        val bundle : Bundle?= intent.extras
        val id = bundle!!.getInt("id")

        hideActionBar()
        var toolbar = binding.myToolbar
        toolbar.title = ""

        fetchData(id)

        binding.back.setOnClickListener {
            binding?.back?.isChecked = true
            onSupportNavigateUp()
        }

        viewModel.dataLiveData.observe(this) {data ->
            var _isCheked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkFavourite(id)
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
                notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setChannelId(channelId)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentTitle("Favourite Status Changed")
                    .setContentText("changed for ${data.email}")
                    .setSmallIcon(R.drawable.baseline_favorite_24_red)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.baseline_favorite_24_red))
                notificationManager.notify(1234, builder.build())

                _isCheked = !_isCheked
                if (_isCheked) {
                    if (data != null) {
                        viewModel.addFavourite(data.name, data.postId, data.id, data.body, data.email)
                    }
                } else {
                    viewModel.removeFromFavorite(id)
                }

                binding?.favourite?.isChecked = _isCheked
            }

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
                viewModel.getSpecificComment(id)
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

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}