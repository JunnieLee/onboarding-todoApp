package com.example.feature.main

import com.example.domain.model.Content
import com.example.domain.model.TextContent

sealed class MainEffect {
    object NavigateToInputWithAddMode : MainEffect()
    data class NavigateToInputWithModifyMode(val item: com.example.domain.model.TextContent): MainEffect()
    data class ShowToast(val text: String): MainEffect()
}