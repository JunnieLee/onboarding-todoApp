package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { ListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply{
            setContentView(root)
            view = this@MainActivity
            recyclerView.adapter = adapter
            val decoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
            // item 이 추가될때마다 divider가 생기도록 decoration 적용
            recyclerView.addItemDecoration(decoration)
        }

        lifecycleScope.launch{
            viewModel.contentList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    binding.emptyTextView.isVisible = it.isEmpty()
                    binding.recyclerView.isVisible = it.isNotEmpty()
                    adapter.submitList(it)
                }
        }
    }

    fun onClickAdd(){

    }
}