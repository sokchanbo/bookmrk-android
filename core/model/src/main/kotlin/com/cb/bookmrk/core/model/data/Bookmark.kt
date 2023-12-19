package com.cb.bookmrk.core.model.data

data class Bookmark(
    val id: Long,
    val title: String,
    val link: String,
    val imageUrl: String,
    val collection: Collection?
)
