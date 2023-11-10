package com.example.feature.input


sealed class InputEffect {
    data class ShowToast(val text: String): InputEffect() // content가 비어있는 상태에서 저장 버튼을 누르면 "내용을 작성해주세요" Toast가 뜨도록
    object NavigateToMain: InputEffect()
}