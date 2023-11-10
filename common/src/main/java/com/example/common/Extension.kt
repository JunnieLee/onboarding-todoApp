package com.example.common

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


inline fun <T> AppCompatActivity.observe(flow: Flow<T>, crossinline action: (T) -> Unit) {
    flow.flowWithLifecycle(this.lifecycle)
        .onEach {
            action.invoke(it)
        }
        .launchIn(this.lifecycleScope)
}