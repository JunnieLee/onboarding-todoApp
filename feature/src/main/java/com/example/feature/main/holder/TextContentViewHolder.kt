package com.example.feature.main.holder

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.common.adapter.AutoBindViewHolder
import com.example.common.adapter.HolderEvent
import com.example.domain.model.TextContent
import com.example.feature.R
import com.example.feature.databinding.ItemContentBinding
import com.example.feature.main.MainActivity
import com.example.feature.main.model.TextContentUI

class TextContentViewHolder(
    view: View,
    event: HolderEvent,
    ) : AutoBindViewHolder<TextContentUI, ToDoHolderEvent>(view, event) { // tmp

    private val binding = ItemContentBinding.bind(view)

    override fun bind(item: TextContentUI) = with (binding){

        this.contentCheckBox.let{
            it.text = item.content
            it.isChecked = item.isChecked
            it.setOnCheckedChangeListener { _, _ ->
                event.onItemCheck(item)
            }
        }
        this.memoTextView.text = item.memo

        this.editButton.setOnClickListener { event.onClickEditButton(item)}
        this.deleteButton.setOnClickListener { event.onClickDeleteButton(item) }

        this.toggleDoneButton.let{
            it.setOnClickListener {
                event.onClickToggleIsDoneButton(item)
            }
            val toggleButtonColor = if (item.isDone) {Color.BLUE} else {Color.GRAY}
            it.setColorFilter(toggleButtonColor)
        }

        this.contentCheckBox.paintFlags = if (item.isDone){
            Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            0
        }

    }

    companion object {
        val CREATOR : (ViewGroup, HolderEvent) -> TextContentViewHolder = { parent, event ->
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content, parent, false)
            TextContentViewHolder(view, event)
        }
        val DIFF = object : DiffUtil.ItemCallback<TextContentUI>(){
            override fun areItemsTheSame(oldItem: TextContentUI, newItem: TextContentUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TextContentUI, newItem: TextContentUI): Boolean {
                return oldItem == newItem
            }

        }
    }


}