package com.example.domain.model

import java.io.Serializable

abstract class Content : Serializable {
    abstract val id: Int
}

enum class ViewType {
    TEXT,
    // IMAGE,
    // VIDEO,
}