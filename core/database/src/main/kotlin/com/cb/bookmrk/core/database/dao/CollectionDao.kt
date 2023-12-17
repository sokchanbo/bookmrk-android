package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.cb.bookmrk.core.database.model.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceCollectionEntity(collection: CollectionEntity): Long
}
