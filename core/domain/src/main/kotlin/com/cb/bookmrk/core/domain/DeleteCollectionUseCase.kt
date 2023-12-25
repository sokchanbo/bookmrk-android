package com.cb.bookmrk.core.domain

import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val bookmarksRepository: BookmarksRepository
) {

    suspend operator fun invoke(collectionId: Long) {
        collectionsRepository.getCollectionWithBookmarks(collectionId).first()?.let { collection ->
            bookmarksRepository.moveBookmarksToTrash(collection.bookmarks)
            collectionsRepository.deleteCollection(collectionId)
        }
    }
}
