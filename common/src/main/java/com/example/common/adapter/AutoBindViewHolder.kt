package com.example.common.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AutoBindViewHolder<T, V: HolderEvent>(
    view: View,
    event: HolderEvent // tmp
) : RecyclerView.ViewHolder(view){

    val context: Context? = itemView.context

    val event: V = event as V

    abstract fun bind(item:T)

    open fun bind(item:T, handler: Any){
        bind(item)
    }

    internal fun bindInternal(item:Any){
        try{
            bind(item as T)
        } catch (e:TypeCastException){
            throw HolderItemTypeNotMatchedException()
        }
    }


    class HolderItemTypeNotMatchedException:
        Exception("AutoBindViewHolder item type does not match Adapter's item type")

}
