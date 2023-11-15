package com.example.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import com.example.domain.usecase.AddToDoUseCase
import com.example.domain.usecase.DeleteMultipleToDosUseCase
import com.example.domain.usecase.DeleteToDoUseCase
import com.example.domain.usecase.LoadToDoListUseCase
import com.example.domain.usecase.ModifyToDoUseCase
import com.example.feature.main.model.TextContentUI
import com.example.feature.main.model.toTextContent
import com.example.feature.main.model.toTextContentUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadToDoListUseCase: com.example.domain.usecase.LoadToDoListUseCase,
    private val deleteToDoUseCase: com.example.domain.usecase.DeleteToDoUseCase,
    private val deleteMultipleToDosUseCase: com.example.domain.usecase.DeleteMultipleToDosUseCase,
    private val modifyToDoUseCase: com.example.domain.usecase.ModifyToDoUseCase,
): BaseViewModel<MainAction, MainState, MainEffect, MainReducer>(MainState(title = "", contentList = listOf())){

    override fun action(action: MainAction) {

        viewModelScope.launch(Dispatchers.IO) {
            when (action) {
                MainAction.OnStart -> {
                    fetchListInfoFromDB() // UI state의 list 초기값은 비어있으니 DB 데이터로 초기화해줌
                }
                MainAction.OnClickAdd -> {
                    emitEffect(MainEffect.NavigateToInputWithAddMode)
                }
                is MainAction.OnItemCheck -> {
                    // item check 내역 수정 -> (isChecked는 UI State에만 있는 프로퍼티이므로 DB 관련 오퍼레이션은 필요 X)
                    val updatedItem = action.item.copy(isChecked=!action.item.isChecked)
                    // UI단 state 에서 특정 아이템 정보 업데이트
                    updateSingleItemInfoOnlyInUIState(updatedItem)
                }
                is MainAction.OnClickToggleIsDoneButton -> {

                    // item done 내역 수정
                    val updatedItem = action.item.copy(isDone=!action.item.isDone)
                    // (1) UI단 state 에서 특정 아이템 정보 업데이트
                    updateSingleItemInfoOnlyInUIState(updatedItem)
                    // (2) DB 단에서 특정 아이템 정보 업데이트
                    launch(Dispatchers.IO) {
                        modifyToDoUseCase.invoke(updatedItem.toTextContent())
                    }

                }
                is MainAction.OnClickEdit -> {
                    emitEffect(MainEffect.NavigateToInputWithModifyMode(action.item.toTextContent()))
                }
                is MainAction.OnClickDelete -> {
                    deleteSingleItem(action.item)
                    emitEffect(MainEffect.ShowToast("single delete completed"))
                }
                is MainAction.OnClickMultipleDelete -> {
                    deleteMultipleItems(action.items)
                    fetchListInfoFromDB() // multiple 아이템 삭제 후 리스트 갱신
                    // 더이상 UI state 에서만 가지고 있는 IsChecked 프로퍼티가 필요없으니 이때 DB랑 동기화해줌
                    emitEffect(MainEffect.ShowToast("total of ${action.items.size} deleted"))
                }
            }
        }

    }

    // UI단 state 에서 특정 아이템 정보 업데이트
    private suspend fun updateSingleItemInfoOnlyInUIState(item: TextContentUI){
        emitReducer(MainReducer.UpdateSingleContentInfoAndUpdateContentList(item))
    }

    private suspend fun deleteSingleItem(item: TextContentUI){
        // (1) UI 단 state 에서 delete 처리
        emitReducer(MainReducer.DeleteItemAndUpdateContentList(mutableListOf(item)))
        // (2) DB 단에서 delete 처리
        deleteToDoUseCase.invoke(item.toTextContent())
    }

    private suspend fun deleteMultipleItems(items: List<TextContentUI>){
        // (1) UI 단 state 에서 delete 처리
        emitReducer(MainReducer.DeleteItemAndUpdateContentList(items))
        // (2) DB 단에서 delete 처리
        deleteMultipleToDosUseCase.invoke(items.map{ item -> item.toTextContent()})
    }

    private suspend fun fetchListInfoFromDB(){
        // DB data fetch
        // & list UI state sync
        // DB랑 sync해줄때는 isChecked가 디폴트 false로 일괄 세팅됨
        // (=isChecked 상태를 유지하고 싶다면 DB sync는 하면 안됨. UI state만 업데이트해야함)
        val newList = loadToDoListUseCase.invoke().map{ item -> item.toTextContentUI()}
        emitReducer(MainReducer.UpdateContentList(list = newList))
    }

}