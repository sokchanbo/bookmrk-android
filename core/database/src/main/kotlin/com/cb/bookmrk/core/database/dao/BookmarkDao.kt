package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.BookmarkWithCollection
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query(
        value = """
            SELECT * FROM bookmarks
            INNER JOIN collections 
            ON collections.id = bookmarks.collection_id
        """
    )
    fun getBookmarkEntities(): Flow<List<BookmarkWithCollection>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceBookmarkEntity(bookmark: BookmarkEntity): Long
}
