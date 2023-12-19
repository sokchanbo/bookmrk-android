package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cb.bookmrk.core.model.data.Bookmark

@Entity(
    tableName = "bookmarks",
    foreignKeys = [
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collection_id"]
        )
    ],
    indices = [Index("collection_id")]
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

data class BookmarkWithCollection(
    @Embedded()
    val collection: CollectionEntity?,
    @Embedded
    val bookmark: BookmarkEntity
)

fun BookmarkEntity.asExternalModel() = Bookmark(
    id = id,
    title = title,
    imageUrl = imageUrl,
    link = link,
    collection = null
)

fun BookmarkWithCollection.asExternalModel() = Bookmark(
    id = bookmark.id,
    title = bookmark.title,
    link = bookmark.link,
    imageUrl = bookmark.imageUrl,
    collection = collection?.asExternalModel()
)
