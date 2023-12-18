package com.cb.bookmrk.core.data.di

import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.BookmarksRepositoryImpl
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import com.cb.bookmrk.core.data.repository.CollectionsRepositoryImpl
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.data.repository.GroupsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsGroupsRepository(
        groupsRepository: GroupsRepositoryImpl
    ): GroupsRepository

    @Binds
    fun bindsCollectionsRepository(
        collectionsRepository: CollectionsRepositoryImpl
    ): CollectionsRepository

    @Binds
    fun bindsBookmarksRepository(
        bookmarksRepository: BookmarksRepositoryImpl
    ): BookmarksRepository
}
