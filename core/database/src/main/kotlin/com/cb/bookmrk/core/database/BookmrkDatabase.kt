package com.cb.bookmrk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cb.bookmrk.core.database.dao.CollectionDao
import com.cb.bookmrk.core.database.dao.GroupDao
import com.cb.bookmrk.core.database.model.CollectionEntity
import com.cb.bookmrk.core.database.model.GroupEntity

@Database(
    entities = [
        GroupEntity::class,
        CollectionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class BookmrkDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun collectionDao(): CollectionDao
}
