package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.cb.bookmrk.core.database.model.BookmarkEntity

@Dao
interface BookmarkDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceBookmarkEntity(bookmark: BookmarkEntity): Long
}
