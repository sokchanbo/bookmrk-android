package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.BookmarkWithCollection
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface BookmarkDao {

    @Transaction
    @Query(
        value = """
            SELECT b.*, collections.name FROM bookmarks as b
            LEFT JOIN collections 
            ON collections.id = b.collection_id
            WHERE deleted_date IS NULL
        """
    )
    fun getBookmarkEntities(): Flow<List<BookmarkWithCollection>>

    @Transaction
    @Query(
        value = """
            SELECT b.*, collections.name FROM bookmarks as b
            LEFT JOIN collections 
            ON collections.id = b.collection_id
            WHERE deleted_date IS NOT NULL
        """
    )
    fun getTrashBookmarkEntities(): Flow<List<BookmarkWithCollection>>

    @Query(
        value = """
            SELECT COUNT(bookmarks.id)
            FROM bookmarks
            WHERE bookmarks.collection_id == :collectionId
        """
    )
    fun countBookmarkEntity(collectionId: Long): Flow<Int>

    @Query(
        value = """
            SELECT COUNT(bookmarks.id)
            FROM bookmarks
            WHERE bookmarks.deleted_date IS NULL
        """
    )
    fun countAllBookmarkEntity(): Flow<Int>

    @Query(
        value = """
            SELECT COUNT(bookmarks.id)
            FROM bookmarks
            WHERE bookmarks.collection_id IS NULL 
            AND bookmarks.deleted_date IS NULL
        """
    )
    fun countUnsortedBookmark(): Flow<Int>

    @Query(
        value = """
            SELECT COUNT(bookmarks.id)
            FROM bookmarks
            WHERE bookmarks.deleted_date IS NOT NULL
        """
    )
    fun countTrashBookmark(): Flow<Int>

    @Query(
        value = """
            SELECT * FROM bookmarks
            WHERE collection_id IS NULL AND deleted_date IS NULL
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

    @Query(
        value = """
            SELECT * FROM bookmarks
            WHERE bookmarks.id = :id
        """
    )
    fun getBookmarkById(id: Long): Flow<BookmarkEntity?>

    @Query(
        value = """
            SELECT b.*, collections.name FROM bookmarks as b
            LEFT JOIN collections 
            ON collections.id = b.collection_id
            WHERE b.id = :id
        """
    )
    fun getBookmarkWithCollection(id: Long): Flow<BookmarkWithCollection?>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceBookmarkEntity(bookmark: BookmarkEntity): Long

    @Update
    suspend fun updateBookmarkEntity(bookmark: BookmarkEntity)

    @Query(
        value = """
            UPDATE bookmarks 
            SET deleted_date = :deletedDate, collection_id = NULL
            WHERE bookmarks.id = :id
        """
    )
    suspend fun moveBookmarkEntityToTrash(id: Long, deletedDate: Date)

    @Query(
        value = """
            UPDATE bookmarks
            SET is_added_to_favorite = :addToFavorite
            WHERE bookmarks.id = :id
        """
    )
    suspend fun addToFavorite(id: Long, addToFavorite: Boolean)

    @Query(
        value = """
            DELETE FROM bookmarks
            WHERE bookmarks.id = :id
        """
    )
    suspend fun deleteBookmarkEntity(id: Long)

    @Query(
        value = """
            UPDATE bookmarks 
            SET deleted_date = NULL
            WHERE bookmarks.id = :id
        """
    )
    suspend fun restoreBookmarkEntity(id: Long)
}
