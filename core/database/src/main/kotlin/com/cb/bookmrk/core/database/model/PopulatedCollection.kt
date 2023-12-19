package com.cb.bookmrk.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.cb.bookmrk.core.model.data.Collection

data class PopulatedCollection(
    @Embedded
    val entity: CollectionEntity,
    @Relation(parentColumn = "id", entityColumn = "collection_id", entity = BookmarkEntity::class)
    val bookmarks: List<BookmarkEntity>
)

fun PopulatedCollection.asExternalModel() = Collection(
    id = entity.id,
    name = entity.name,
    isPrivate = entity.isPrivate,
    bookmarks = bookmarks.map(BookmarkEntity::asExternalModel)
)
