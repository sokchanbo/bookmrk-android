package com.cb.bookmrk.core.model.data

import java.util.Date
import java.util.UUID

data class Bookmark(
    val id: Long,
    val title: String,
    val link: String,
    val imageUrl: String,
    val description: String?,
    val note: String?,
    val createdDate: Date,
    val deletedDate: Date?,
    val collection: Collection?,
    val uuid: UUID = UUID.randomUUID()
)

data class UpdateBookmark(
    val id: Long,
    val title: String,
    val description: String?,
    val note: String?,
    val link: String,
    val collectionId: Long?,
)
