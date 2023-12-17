package com.cb.bookmrk.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.cb.bookmrk.core.model.data.Group

data class PopulatedGroup(
    @Embedded
    val entity: GroupEntity,
    @Relation(parentColumn = "id", entityColumn = "group_id", entity = CollectionEntity::class)
    val collections: List<CollectionEntity>
)

fun PopulatedGroup.asExternalModel() = Group(
    id = entity.id,
    title = entity.title,
    collections = collections.map(CollectionEntity::asExternalModel)
)
