package com.example.feature.input

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.common.observe
import com.example.feature.databinding.ActivityInputBinding
import com.example.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding
    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.action(InputAction.OnCreate(intent.getIntExtra(ITEM_ID, -1))) // 내부 state 초기값 세팅

        setupUi()
        observeState()
        observeEffect()
    }

    private fun setupUi() = with(binding) {

        contentEdit.addTextChangedListener { text ->
            viewModel.action(com.example.feature.input.InputAction.OnContentTextInputChange(text.toString()))
        }

        memoEdit.addTextChangedListener { text ->
            viewModel.action(com.example.feature.input.InputAction.OnMemoTextInputChange(text.toString()))
        }

        confirmButton.setOnClickListener {
            viewModel.action(com.example.feature.input.InputAction.OnClickSaveButton)
        }
    }

    private fun observeState() = observe(viewModel.state) { state ->
        Log.e("daesoon","${state.mode}")
        renderContentEdit(state.content)
        renderMemoEdit(state.memo)
        binding.confirmButton.isEnabled = state.content.isNotEmpty()
    }

    private fun observeEffect() = observe(viewModel.effect) {
        when (it) {
            InputEffect.NavigateToMain -> Intent(
                this@InputActivity,
                MainActivity::class.java
            ).run {
                this@InputActivity.startActivity(this)
            }

            is InputEffect.ShowToast -> Toast.makeText(
                this@InputActivity,
                it.text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun renderContentEdit(input: String) = with(binding.contentEdit) {
        if (text.toString() != input) {
            setText(input)
        }
    }

    private fun renderMemoEdit(input: String) = with(binding.memoEdit) {
        if (text.toString() != input) {
            setText(input)
        }
    }

    companion object {
        private const val ITEM_ID = "item_id"

        fun start(context: Context, itemId: Int? = null) {
            Intent(context, InputActivity::class.java).apply {
                putExtra(ITEM_ID, itemId)
            }.run {
                context.startActivity(this)
            }
        }
    }

    /*
    override fun onSupportNavigateUp():Boolean{
        finish()
        return true
    }
     */
}