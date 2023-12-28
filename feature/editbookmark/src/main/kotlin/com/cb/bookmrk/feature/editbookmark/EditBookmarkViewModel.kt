package com.cb.bookmrk.feature.editbookmark

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.model.data.UpdateBookmark
import com.cb.bookmrk.feature.editbookmark.navigation.EditBookmarkArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditBookmarkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    groupsRepository: GroupsRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val editBookmarkArgs = EditBookmarkArgs(savedStateHandle)

    val bookmark: StateFlow<Bookmark?> =
        bookmarksRepository.getBookmarkWithCollection(editBookmarkArgs.bookmarkId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val groups: StateFlow<List<Group>> =
        groupsRepository.getGroupsWithCollections()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun updateBookmark(updateBookmark: UpdateBookmark) {
        viewModelScope.launch {
            bookmarksRepository.updateBookmark(updateBookmark)
        }
    }
}
