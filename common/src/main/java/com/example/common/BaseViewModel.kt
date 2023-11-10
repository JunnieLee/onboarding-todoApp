package com.example.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<A : Any, S : Any, E : Any, R : Reducer<S>>(
    initialState: S
) : ViewModel() {
    abstract fun action(action: A)

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

    protected val _effect = MutableSharedFlow<E>(replay = 0)
    val effect: Flow<E>
        get() = _effect

    suspend fun withCurrentState(action: suspend (S) -> Unit) {
        action(_state.value)
    }

    //action(S) -> T
    suspend fun <T> withCurrentStateReturn(action: suspend (S) -> T): T {
        return action(_state.value)
    }

    suspend fun emitReducer(reducer: R) {
        withCurrentState { state ->
            _state.update { reducer.reduce(state) }
        }
    }

    suspend fun emitEffect(effect: E) {
        _effect.emit(effect)
    }

    //abstract fun <T> MutableListOf(item: T): Any
}