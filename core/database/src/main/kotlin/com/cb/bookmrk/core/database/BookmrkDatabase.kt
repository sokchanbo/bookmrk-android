package com.cb.bookmrk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cb.bookmrk.core.database.dao.BookmarkDao
import com.cb.bookmrk.core.database.dao.CollectionDao
import com.cb.bookmrk.core.database.dao.GroupDao
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.CollectionEntity
import com.cb.bookmrk.core.database.model.GroupEntity
import com.cb.bookmrk.core.database.util.DateConverter

@Database(
    entities = [
        GroupEntity::class,
        CollectionEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class BookmrkDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun collectionDao(): CollectionDao

    abstract fun bookmarkDao(): BookmarkDao
}
