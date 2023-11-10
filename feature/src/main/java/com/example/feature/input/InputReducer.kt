package com.example.feature.input
import com.example.common.Reducer
import com.example.domain.model.TextContent
import com.example.feature.input.model.InputMode

open class InputReducer(
    reducer: Reducer<InputState>
) : Reducer<InputState> by reducer {

    data class Initialize(
        val mode: InputMode,
        val content: String,
        val memo: String
    ): InputReducer(reducer = {
        it.copy(mode = mode, content = content, memo = memo)
    })

    data class UpdateContentText(
        val text:String
    ): InputReducer(reducer = {
        it.copy(content = text)
    })

    data class UpdateMemoText(
        val text:String
    ): InputReducer(reducer = {
        it.copy(memo = text)
    })

}

