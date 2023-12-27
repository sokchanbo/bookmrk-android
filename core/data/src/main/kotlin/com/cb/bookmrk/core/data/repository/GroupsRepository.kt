package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.database.dao.GroupDao
import com.cb.bookmrk.core.database.model.GroupEntity
import com.cb.bookmrk.core.database.model.PopulatedGroup
import com.cb.bookmrk.core.database.model.asExternalModel
import com.cb.bookmrk.core.model.data.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GroupsRepository {

    suspend fun createGroup(title: String)
    fun getGroupsWithCollections(): Flow<List<Group>>

    fun getGroups(): Flow<List<Group>>

    fun getGroup(id: Long): Flow<Group?>

    suspend fun updateGroup(id: Long, title: String)
}

class GroupsRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao
) : GroupsRepository {

    override suspend fun createGroup(title: String) {
        groupDao.insertOrReplaceGroupEntity(
            GroupEntity(title = title)
        )
    }
    override fun getGroupsWithCollections(): Flow<List<Group>> =
        groupDao.getGroupsWithCollections().map {
            it.map(PopulatedGroup::asExternalModel)
        }

    override fun getGroups(): Flow<List<Group>> =
        groupDao.getGroups().map {
            it.map(GroupEntity::asExternalModel)
        }

    override fun getGroup(id: Long): Flow<Group?> =
        groupDao.getGroupEntity(id).map { it?.asExternalModel() }

    override suspend fun updateGroup(id: Long, title: String) {
        groupDao.updateGroupEntity(GroupEntity(title = title, id = id))
    }
}
