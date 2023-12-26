package com.cb.bookmrk.feature.bookmarks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.feature.bookmarks.navigation.BookmarksArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class BookmarksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    bookmarksRepository: BookmarksRepository,
    collectionsRepository: CollectionsRepository
) : ViewModel() {

    val bookmarksArgs = BookmarksArgs(savedStateHandle)

    val bookmarks: StateFlow<List<Bookmark>> =
        bookmarksRepository.getBookmarks(
            bookmarksArgs.homeScreenClickType,
            bookmarksArgs.collectionId
        ).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val collection: StateFlow<Collection?> =
        if (bookmarksArgs.collectionId == null) {
            emptyFlow()
        } else {
            collectionsRepository.getCollection(bookmarksArgs.collectionId)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )
}
