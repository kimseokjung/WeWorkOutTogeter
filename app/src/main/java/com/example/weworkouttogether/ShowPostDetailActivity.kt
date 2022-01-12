package com.example.weworkouttogether

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weworkouttogether.databinding.ActivityShowPostDetailBinding

class ShowPostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPostDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var tv = intent.getStringExtra("test")

        binding.testBack
        binding.showPostTv.text = tv
    }
}