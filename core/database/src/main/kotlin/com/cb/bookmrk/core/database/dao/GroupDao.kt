package com.cb.bookmrk.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cb.bookmrk.core.database.model.GroupEntity
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
    fun getGroupsWithCollections(): Flow<List<PopulatedGroup>>

    @Query(
        value = """
            SELECT * FROM groups
        """
    )
    fun getGroups(): Flow<List<GroupEntity>>

    @Query(
        value = """
            SELECT * FROM groups
            WHERE id = :id
        """
    )
    fun getGroupEntity(id: Long): Flow<GroupEntity?>

    @Insert
    suspend fun insertOrReplaceGroupEntity(group: GroupEntity): Long

    @Update
    suspend fun updateGroupEntity(group: GroupEntity)
}
