package com.example.domain.model

data class TextContent (
    override val id : Int = 0,
    val content : String,
    val memo:String ?= null,
    val isDone:Boolean = false,
): Content()


// extension functions

fun List<Content>.filterIsDone(): List<Content> {
    return this.filter { (it as? TextContent)?.isDone ?: true }
}

