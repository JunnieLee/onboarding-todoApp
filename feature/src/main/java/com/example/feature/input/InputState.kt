package com.example.feature.input

import com.example.feature.input.model.InputMode

data class InputState (
    val mode: InputMode,
    val content: String,
    val memo: String
)

