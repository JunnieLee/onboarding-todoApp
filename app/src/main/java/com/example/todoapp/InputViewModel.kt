package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.ContentEntity
import com.example.todoapp.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val contentRepository: ContentRepository
): ViewModel(){
    private val _doneEvent = MutableLiveData<Unit>()
    val doneEvent : LiveData<Unit> = _doneEvent

    var content = MutableLiveData<String>()
    var memo = MutableLiveData<String?>()
    var item: ContentEntity? = null

    fun initData(item:ContentEntity){
        this.item = item
        content.value = item.content
        memo.value = item.memo
    }

    fun insertData(){
        content.value?.let{content->
                viewModelScope.launch(Dispatchers.IO){
                    contentRepository.insert(
                        item?.copy(
                            content = content,
                            memo = memo.value
                        )?: ContentEntity(content=content, memo=memo.value)
                    ) // suspend function이기 때문에 요 insert가 다 실행되고 난 뒤에 아래 postValue가 호출된다.
                    _doneEvent.postValue(Unit)
                }
        }
    }
}