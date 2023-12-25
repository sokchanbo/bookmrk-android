package com.cb.bookmrk.core.model.data

data class Group(
    val id: Long,
    val title: String,
    val collections: List<Collection> = emptyList()
)
