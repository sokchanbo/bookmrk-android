package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmarks",
    foreignKeys = [
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collection_id"]
        )
    ]
)
data class BookmarkEntity(
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val link: String,
    @ColumnInfo(name = "collection_id")
    val collectionId: Long?,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
