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
    val description: String? = null,
    val note: String? = null,
    @ColumnInfo(name = "is_added_to_favorite")
    val isAddedToFavorite: Boolean = false,
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
    description = description,
    note = note,
    createdDate = createdDate,
    deletedDate = deletedDate,
    addedToFavorite = isAddedToFavorite,
    collection = null
)

fun BookmarkWithCollection.asExternalModel() = Bookmark(
    id = bookmark.id,
    title = bookmark.title,
    link = bookmark.link,
    imageUrl = bookmark.imageUrl,
    description = bookmark.description,
    note = bookmark.note,
    createdDate = bookmark.createdDate,
    deletedDate = bookmark.deletedDate,
    addedToFavorite = bookmark.isAddedToFavorite,
    collection = collection?.let {
        Collection(
            id = bookmark.collectionId!!,
            name = it.name,
            isPrivate = false
        )
    },
)
