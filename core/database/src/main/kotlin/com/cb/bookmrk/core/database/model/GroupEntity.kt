package com.cb.bookmrk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cb.bookmrk.core.model.data.Group

@Entity(tableName = "groups")
data class GroupEntity(
    val title: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)

fun GroupEntity.asExternalModel(): Group =
    Group(
        id = id,
        title = title,
        collections = emptyList()
    )
