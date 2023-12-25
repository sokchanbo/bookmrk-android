package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.cb.bookmrk.core.database.model.CollectionEntity
import com.cb.bookmrk.core.database.model.CollectionWithGroup
import com.cb.bookmrk.core.database.model.PopulatedCollection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Query(
        value = """
            SELECT * FROM collections
            WHERE collections.id = :id
        """
    )
    fun getCollectionEntityById(id: Long): Flow<CollectionEntity?>

    @Query(
        value = """
            SELECT * FROM collections
            WHERE collections.id = :id
        """
    )
    fun getCollectionWithBookmarks(id: Long): Flow<PopulatedCollection?>

    @Query(
        value = """
            SELECT * FROM collections
            LEFT JOIN groups ON groups.id = collections.group_id
            WHERE collections.id = :id
        """
    )
    fun getCollectionWithGroupById(id: Long): Flow<CollectionWithGroup?>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceCollectionEntity(collection: CollectionEntity): Long

    @Update
    suspend fun updateCollectionEntity(collection: CollectionEntity)

    @Query(
        value = """
            DELETE FROM collections
            WHERE collections.id = :id
        """
    )
    suspend fun deleteCollection(id: Long)
}
