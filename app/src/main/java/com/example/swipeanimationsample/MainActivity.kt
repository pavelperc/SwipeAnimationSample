package com.example.swipeanimationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swipeanimationsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonReset.setOnClickListener {
            binding.swipeView.reset()
        }
    }
}