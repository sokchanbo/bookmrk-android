package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cb.bookmrk.core.model.data.Collection

@Entity(
    tableName = "collections",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["group_id"]
        )
    ]
)
data class CollectionEntity(
    val name: String,
    @ColumnInfo(name = "is_private")
    val isPrivate: Boolean,
    @ColumnInfo(name = "group_id")
    val groupId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

fun CollectionEntity.asExternalModel() = Collection(
    id = id,
    name = name,
    isPrivate = isPrivate
)
