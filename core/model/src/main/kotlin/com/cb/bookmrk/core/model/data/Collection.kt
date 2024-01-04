package com.cb.bookmrk.core.model.data

data class Collection(
    val id: Long,
    val name: String,
    val group: Group? = null,
    val bookmarks: List<Bookmark> = emptyList(),
    val bookmarkCount: Int? = null
)
