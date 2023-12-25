package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.database.dao.CollectionDao
import com.cb.bookmrk.core.database.model.CollectionEntity
import com.cb.bookmrk.core.database.model.asExternalModel
import com.cb.bookmrk.core.model.data.Collection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CollectionsRepository {

    fun getCollection(collectionId: Long): Flow<Collection?>

    fun getCollectionWithGroup(collectionId: Long): Flow<Collection?>

    suspend fun createCollection(name: String, isPrivate: Boolean, groupId: Long)

    suspend fun updateCollection(id: Long, name: String, groupId: Long)
}

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionsRepository {

    override fun getCollection(collectionId: Long): Flow<Collection?> =
        collectionDao.getCollectionEntityById(collectionId)
            .map { it?.asExternalModel() }

    override fun getCollectionWithGroup(collectionId: Long): Flow<Collection?> =
        collectionDao.getCollectionWithGroupById(collectionId)
            .map { it?.asExternalModel() }

    override suspend fun createCollection(name: String, isPrivate: Boolean, groupId: Long) {
        collectionDao.insertOrReplaceCollectionEntity(
            CollectionEntity(name = name, isPrivate = isPrivate, groupId = groupId)
        )
    }

    override suspend fun updateCollection(id: Long, name: String, groupId: Long) {
        collectionDao.updateCollectionEntity(
            CollectionEntity(
                name = name,
                groupId = groupId,
                isPrivate = true,
                id = id
            )
        )
    }
}
