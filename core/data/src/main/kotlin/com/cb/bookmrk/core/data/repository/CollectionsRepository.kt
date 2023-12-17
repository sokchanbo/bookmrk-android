package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.database.dao.CollectionDao
import com.cb.bookmrk.core.database.model.CollectionEntity
import javax.inject.Inject

interface CollectionsRepository {
    suspend fun createCollection(name: String, isPrivate: Boolean, groupId: Long)
}

class CollectionsRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionsRepository {

    override suspend fun createCollection(name: String, isPrivate: Boolean, groupId: Long) {
        collectionDao.insertOrReplaceCollectionEntity(
            CollectionEntity(name = name, isPrivate = isPrivate, groupId = groupId)
        )
    }
}
