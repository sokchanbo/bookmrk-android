package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.cb.bookmrk.core.database.model.PopulatedGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Transaction
    @Query(
        value = """
            SELECT * FROM groups
        """
    )
    fun getGroupEntities(): Flow<List<PopulatedGroup>>
}
