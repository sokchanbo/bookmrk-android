package com.cb.bookmrk.core.database.di

import com.cb.bookmrk.core.database.BookmrkDatabase
import com.cb.bookmrk.core.database.dao.CollectionDao
import com.cb.bookmrk.core.database.dao.GroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesGroupDao(
        database: BookmrkDatabase
    ): GroupDao = database.groupDao()

    @Provides
    fun providesCollectionDao(
        database: BookmrkDatabase
    ): CollectionDao = database.collectionDao()
}
