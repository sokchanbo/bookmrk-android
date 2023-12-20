package com.cb.bookmrk.core.model.data

import java.util.Date

data class Bookmark(
    val id: Long,
    val title: String,
    val link: String,
    val imageUrl: String,
    val createdDate: Date,
    val collection: Collection?
)
