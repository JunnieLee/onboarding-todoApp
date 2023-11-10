package com.example.feature.main

import com.example.domain.model.Content
import com.example.feature.main.model.TextContentUI

data class MainState(
    val title: String,
    val contentList: List<TextContentUI>
)