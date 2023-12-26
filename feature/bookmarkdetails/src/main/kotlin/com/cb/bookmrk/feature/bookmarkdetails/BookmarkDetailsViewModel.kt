package com.cb.bookmrk.feature.bookmarkdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.feature.bookmarkdetails.navigation.BookmarkDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class BookmarkDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val bookmarkDetailsArgs = BookmarkDetailsArgs(savedStateHandle)

    val bookmark: StateFlow<Bookmark?> =
        bookmarksRepository.getBookmarkById(bookmarkDetailsArgs.bookmarkId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
