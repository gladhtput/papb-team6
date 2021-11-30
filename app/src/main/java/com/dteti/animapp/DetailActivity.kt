package com.dteti.animapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dteti.animapp.databinding.ActivityDetailBinding
import com.dteti.animapp.presentation.animedetails.AnimeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val detailViewModel: AnimeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        setContentView(R.layout.activity_detail)

        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.apply {
            lifecycleOwner = this@DetailActivity
            this.viewModel = detailViewModel
        }

        detailViewModel.onBackPressed = {
            super.onBackPressed()
        }

        val backButton = findViewById<ImageView>(R.id.iv_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        Log.d("DetailActivity", "Loading anime details for ID: ${intent.getStringExtra("animeId")}.")
        detailViewModel.animeId = (intent.getStringExtra("animeId") ?: "0").toInt()
    }
}