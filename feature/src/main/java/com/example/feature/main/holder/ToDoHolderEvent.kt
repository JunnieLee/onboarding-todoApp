package com.example.feature.main.holder

import com.example.common.adapter.HolderEvent
import com.example.domain.model.TextContent
import com.example.feature.main.model.TextContentUI

interface ToDoHolderEvent: HolderEvent {
    fun onClickEditButton(item: TextContentUI)
    fun onClickDeleteButton(item: TextContentUI):Boolean
    fun onItemCheck(item: TextContentUI)
    fun onClickToggleIsDoneButton(item: TextContentUI)
}