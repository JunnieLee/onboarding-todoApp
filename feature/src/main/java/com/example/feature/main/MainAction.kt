package com.example.feature.main

import com.example.domain.model.TextContent
import com.example.feature.main.model.TextContentUI

sealed class MainAction {

    object OnStart: MainAction()

    object OnClickAdd: MainAction()

    data class OnItemCheck(val item: TextContentUI): MainAction()

    data class OnClickEdit(val item: TextContentUI): MainAction()

    data class OnClickDelete(val item: TextContentUI): MainAction()

    data class OnClickMultipleDelete(val items: List<TextContentUI>): MainAction()

    data class OnClickToggleIsDoneButton(val item: TextContentUI): MainAction()
}