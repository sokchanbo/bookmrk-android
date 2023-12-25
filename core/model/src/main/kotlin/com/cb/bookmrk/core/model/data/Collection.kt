package com.cb.bookmrk.core.model.data

data class Collection(
    val id: Long,
    val name: String,
    val isPrivate: Boolean,
    val group: Group? = null,
    val bookmarks: List<Bookmark> = emptyList()
)
