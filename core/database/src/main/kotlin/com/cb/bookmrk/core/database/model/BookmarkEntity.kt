package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.Collection
import java.util.Date

@Entity(
    tableName = "bookmarks",
    foreignKeys = [
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collection_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index("collection_id")]
)
data class BookmarkEntity(
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val link: String,
    @ColumnInfo(name = "created_date")
    val createdDate: Date,
    @ColumnInfo(name = "collection_id")
    val collectionId: Long?,
    @ColumnInfo(name = "deleted_date")
    val deletedDate: Date? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

data class BookmarkWithCollection(
    @Embedded
    val bookmark: BookmarkEntity,

    @Embedded
    val collection: EmbeddedCollection?
)

fun BookmarkEntity.asExternalModel() = Bookmark(
    id = id,
    title = title,
    imageUrl = imageUrl,
    link = link,
    createdDate = createdDate,
    collection = null
)

fun BookmarkWithCollection.asExternalModel() = Bookmark(
    id = bookmark.id,
    title = bookmark.title,
    link = bookmark.link,
    imageUrl = bookmark.imageUrl,
    createdDate = bookmark.createdDate,
    collection = collection?.let {
        Collection(
            id = bookmark.collectionId!!,
            name = it.name,
            isPrivate = false
        )
    }
)
