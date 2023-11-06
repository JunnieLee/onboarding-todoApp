package com.example.todoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.todoapp.databinding.ActivityInputBinding
import com.example.todoapp.model.ContentEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private val viewModel : InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater).apply{
            setContentView(root)
            lifecycleOwner = this@InputActivity // viewModel에서 lifecycle 사용하니까 lifecycle owener 등록해줌
            viewModel = this@InputActivity.viewModel
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (intent.getSerializableExtra(ITEM) as? ContentEntity)?.let{
            viewModel.initData(it)
        }

        viewModel.doneEvent.observe(this){
            Toast.makeText(this, "완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp():Boolean{
        finish()
        return true
    }

    companion object {
        private const val ITEM = "item"

        fun start(context: Context, item: ContentEntity?=null){
            Intent(context, InputActivity::class.java).apply{
                putExtra(ITEM, item)
            }.run{
                context.startActivity(this)
            }
        }
    }
}