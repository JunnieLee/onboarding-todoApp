package com.example.feature.input.model

sealed class InputMode {
    object NotSet: InputMode()
    object Add: InputMode()
    data class Modify(val id: Int): InputMode()
}