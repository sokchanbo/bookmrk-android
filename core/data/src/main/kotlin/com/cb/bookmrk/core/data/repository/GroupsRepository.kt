package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.database.dao.GroupDao
import com.cb.bookmrk.core.database.model.PopulatedGroup
import com.cb.bookmrk.core.database.model.asExternalModel
import com.cb.bookmrk.core.model.data.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GroupsRepository {
    fun getGroups(): Flow<List<Group>>
}

class GroupsRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao
) : GroupsRepository {

    override fun getGroups(): Flow<List<Group>> =
        groupDao.getGroupEntities().map {
            it.map(PopulatedGroup::asExternalModel)
        }
}
