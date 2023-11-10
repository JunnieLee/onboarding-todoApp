package com.example.feature.main

import com.example.common.Reducer
import com.example.domain.model.Content
import com.example.feature.main.model.TextContentUI

open class MainReducer(
    reducer: Reducer<MainState>
) : Reducer<MainState> by reducer {

    data class UpdateContentList(
        val list: List<TextContentUI>
    ) : MainReducer(reducer = {
        it.copy(contentList = list)
    })

    data class UpdateSingleContentInfoAndUpdateContentList(
        val itemToUpdate: TextContentUI
    ) : MainReducer(reducer = {
        val newList = it.contentList.toMutableList()
            .map { originalItem -> if (originalItem.id == itemToUpdate.id) itemToUpdate else originalItem }
        it.copy(contentList = newList)
    })

    data class DeleteItemAndUpdateContentList(
        val itemsToDelete: List<TextContentUI>
    ) : MainReducer(reducer = {
        val newList = it.contentList.toMutableList()
        newList.removeAll(itemsToDelete)
        it.copy(contentList = newList)
    })

    /*
    data class UpdateTitle(
        val title: String
    ): MainReducer(reducer = {
        it.copy(title = title)
    })

    data class AddContent(
        val added: Content
    ): MainReducer(reducer = {
        val newList = it.contentList.toMutableList()
        newList.add(added)
        it.copy(contentList = newList)
    })
     */

}