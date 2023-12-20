package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.cb.bookmrk.core.database.model.CollectionEntity
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

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceCollectionEntity(collection: CollectionEntity): Long
}
