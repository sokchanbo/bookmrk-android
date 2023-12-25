package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.BookmarkWithCollection
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface BookmarkDao {

    @Transaction
    @Query(
        value = """
            SELECT * FROM bookmarks
            LEFT JOIN collections 
            ON collections.id = bookmarks.collection_id
        """
    )
    fun getBookmarkEntities(): Flow<List<BookmarkWithCollection>>

    @Query(
        value = """
            SELECT * FROM bookmarks
            WHERE collection_id IS NULL
        """
    )
    fun getBookmarkEntitiesWithEmptyCollection(): Flow<List<BookmarkEntity>>

    @Query(
        value = """
            SELECT * FROM bookmarks
            WHERE collection_id = :collectionId
        """
    )
    fun getBookmarkEntitiesByCollectionId(collectionId: Long): Flow<List<BookmarkEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceBookmarkEntity(bookmark: BookmarkEntity): Long

    @Query(
        value = """
            UPDATE bookmarks 
            SET deleted_date = :deletedDate, collection_id = NULL
            WHERE bookmarks.id = :id
        """
    )
    suspend fun moveBookmarkEntityToTrash(id: Long, deletedDate: Date)
}
