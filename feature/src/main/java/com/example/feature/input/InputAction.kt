package com.example.feature.input

import com.example.feature.input.model.InputMode

sealed class InputAction {
    data class OnCreate (val id: Int) : InputAction()
    data class OnContentTextInputChange (val newText: String) : InputAction()
    data class OnMemoTextInputChange (val newText: String) : InputAction()
    object OnClickSaveButton : InputAction()
}