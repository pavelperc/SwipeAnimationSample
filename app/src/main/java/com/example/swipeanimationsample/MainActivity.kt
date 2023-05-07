package com.example.swipeanimationsample

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.swipeanimationsample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

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
        binding.swipeView.onSwipeComplete = {
            Snackbar.make(binding.swipeView, "swiped", 500).show()
        }
        setupNestedRecycler()
    }

    private fun setupNestedRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                object : ViewHolder(getRowRecycler()) {}

            override fun getItemCount() = 10

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                ((holder.itemView as RecyclerView).adapter as RowAdapter).rowPosition = position
            }
        }
    }

    private fun getRowRecycler(): RecyclerView {
        return RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = RowAdapter()
        }
    }

    class RowAdapter : RecyclerView.Adapter<ViewHolder>() {
        companion object {
            private const val ROW_SIZE = 10
        }

        var rowPosition: Int = 0
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            object : ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            ) {}

        override fun getItemCount() = ROW_SIZE

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val absolutePosition = 10 * rowPosition + position
            val color = generateColor(absolutePosition)
            holder.itemView.backgroundTintList = ColorStateList.valueOf(color)
            (holder.itemView as TextView).text = absolutePosition.toString()
            holder.itemView.setOnClickListener {
                Snackbar.make(holder.itemView, "click $absolutePosition", 500).show()
            }
        }

        private fun generateColor(number: Int): Int {
            val random = Random(number)
            return ColorUtils.HSLToColor(floatArrayOf(random.nextInt(360).toFloat(), 0.7f, 0.6f))
        }
    }
}