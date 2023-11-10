package com.example.common

fun interface Reducer<S> {
    fun reduce(state: S): S
}