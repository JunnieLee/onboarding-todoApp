package com.example.feature.input

import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.domain.usecase.AddToDoUseCase
import com.example.domain.usecase.GetToDoUseCase
import com.example.domain.usecase.ModifyToDoUseCase
import com.example.feature.input.model.InputMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val addToDoUseCase: AddToDoUseCase,
    private val modifyToDoUseCase: ModifyToDoUseCase,
    private val getToDoUseCase: GetToDoUseCase
) : BaseViewModel<InputAction, InputState, InputEffect, InputReducer>(
    InputState(mode = InputMode.NotSet, content = "", memo = "")
) {
    override fun action(action: InputAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (action) {
                is InputAction.OnCreate -> {
                    setInitialValue(action.id)
                }

                is InputAction.OnContentTextInputChange -> {
                    emitReducer(InputReducer.UpdateContentText(text = action.newText))
                }

                is InputAction.OnMemoTextInputChange -> {
                    emitReducer(InputReducer.UpdateMemoText(text = action.newText))
                }

                is InputAction.OnClickSaveButton -> {
                    handleOnClickSaveButton()
                }
            }
        }
    }

    private suspend fun setInitialValue(id: Int) {
        if (id == UNDEFINED) {
            emitReducer(InputReducer.Initialize(mode = InputMode.Add, "", ""))
        } else {
            val item = getToDoUseCase.invoke(id)
            setupModifyModeIfAvailable(item)
        }
    }

    private suspend fun setupModifyModeIfAvailable(item: com.example.domain.model.TextContent?) {
        if (item == null) {
            emitReducer(InputReducer.Initialize(mode = InputMode.Add, "", ""))
        } else {
            emitReducer(
                InputReducer.Initialize(
                    mode = InputMode.Modify(id = item.id),
                    item.content,
                    item.memo ?: ""
                )
            )
        }
    }

    private suspend fun handleOnClickSaveButton() {
        withCurrentState { state ->
            if (state.content == "") {
                emitEffect(InputEffect.ShowToast("you cannot save items with empty content"))
            } else {
                when (state.mode) {
                    InputMode.Add -> insertData(state.content, state.memo)
                    is InputMode.Modify -> modifyData(state.mode.id, state.content, state.memo)
                    InputMode.NotSet -> {}
                }
                emitEffect(InputEffect.ShowToast("new item data saved"))
                emitEffect(InputEffect.NavigateToMain)
            }
        }
    }

    private suspend fun insertData(content: String, memo: String) {
        addToDoUseCase.invoke(content = content, memo = memo)
    }

    private suspend fun modifyData(id: Int, content: String, memo: String) {
        modifyToDoUseCase.invoke(
            com.example.domain.model.TextContent(id = id, content = content, memo = memo)
        )
    }

    companion object {
        const val UNDEFINED = -1
    }
}