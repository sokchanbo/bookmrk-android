package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val link: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
