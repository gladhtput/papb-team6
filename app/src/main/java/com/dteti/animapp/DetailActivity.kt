package com.dteti.animapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dteti.animapp.databinding.ActivityDetailBinding
import com.dteti.animapp.ui.animedetails.AnimeDetailsViewModel

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val viewModel = ViewModelProvider(this).get(AnimeDetailsViewModel::class.java)

        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.apply {
            lifecycleOwner = this@DetailActivity
            this.viewModel = viewModel
        }

        val backButton = findViewById<ImageView>(R.id.iv_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        viewModel.setAnime(intent.getStringExtra("animeId") ?: "0")
    }
}