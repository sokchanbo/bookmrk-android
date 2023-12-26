package com.cb.bookmrk.core.domain

import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.model.data.HomeScreenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHomeScreenDataUseCase @Inject constructor(
    private val groupsRepository: GroupsRepository,
    private val bookmarksRepository: BookmarksRepository
) {

    operator fun invoke(): Flow<HomeScreenData> =
        combine(
            bookmarksRepository.countAllBookmark(),
            bookmarksRepository.countUnsortedBookmark(),
            bookmarksRepository.countTrashBookmark(),
            groupsRepository.getGroupsWithCollections()
        ) { allBookmarkCount, unsortedBookmarkCount, trashBookmarkCount, groups ->
            val newGroups = ArrayList<Group>()
            for (group in groups) {
                val collections = ArrayList<Collection>()
                for (collection in group.collections) {
                    val bookmarkCount = bookmarksRepository
                        .countBookmark(collection.id)
                        .firstOrNull()
                    collections.add(
                        collection.copy(
                            bookmarkCount = bookmarkCount
                        )
                    )

                }
                newGroups.add(group.copy(collections = collections))
            }
            HomeScreenData(
                allBookmarkCount = allBookmarkCount,
                unsortedBookmarkCount = unsortedBookmarkCount,
                trashBookmarkCount = trashBookmarkCount,
                groups = newGroups
            )
        }
}