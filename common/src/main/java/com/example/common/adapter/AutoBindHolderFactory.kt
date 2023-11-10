package com.example.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

class AutoBindHolderFactory<S:Any> {

    private val creatorMap = mutableMapOf<Int,(ViewGroup, HolderEvent)-> AutoBindViewHolder<*, *>>()
    private val diffMap = mutableMapOf<KClass<out S>, DiffUtil.ItemCallback<out S>>() // KClass? out S ?
    private val typeMap = mutableMapOf<KClass<out S>,Int>()
    private val eventMap =  mutableMapOf<Int, HolderEvent>() // tmp

    fun <T:S> add( // <T:S> ..? --> 앗 extension function 같은건가보옴
        clazz: KClass<out S>, // clazz..?
        diffCallback: DiffUtil.ItemCallback<T>,
        holderEvent: HolderEvent, // tmp
        creator: (ViewGroup, HolderEvent)-> AutoBindViewHolder<T, *> //(ViewGroup, HolderEvent) -> AutoBindViewHolder<T,*>
    ): AutoBindHolderFactory<S> {

        val type = clazz.hashCode()

        creatorMap[type] = creator
        diffMap[clazz] = diffCallback
        typeMap[clazz] = type
        eventMap[type] = holderEvent

        return this
    }

    fun getDiffUtilCallback(clazz: KClass<out S>): DiffUtil.ItemCallback<S>?{
        return diffMap[clazz] as? DiffUtil.ItemCallback<S>
    }

    fun getItemType(clazz:KClass<out S>):Int{
        return typeMap[clazz] ?: 0
    }

    fun createViewHolder(type:Int, parent:ViewGroup): AutoBindViewHolder<*, *> {
        return creatorMap[type]!!.invoke(parent, eventMap[type]!!) // !! ?
    }

}

fun <T:Any> AutoBindHolderFactory<T>.buildAdapter() : AutoBindListAdapter<T> {
    return AutoBindListAdapter(this)
}