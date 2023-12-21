package com.cb.bookmrk.feature.addbookmark

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.BookmarksRepository
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.feature.addbookmark.navigation.AddBookmarkArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddBookmarkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    groupsRepository: GroupsRepository,
    collectionsRepository: CollectionsRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val addBookmarkArgs = AddBookmarkArgs(savedStateHandle)

    val groups: StateFlow<List<Group>> =
        if (addBookmarkArgs.collectionId != null) {
            emptyFlow()
        } else {
            groupsRepository.getGroups()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val collection: StateFlow<Collection?> =
        if (addBookmarkArgs.collectionId == null) {
            emptyFlow()
        } else {
            collectionsRepository.getCollection(addBookmarkArgs.collectionId)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    private val _addBookmarkUiState = MutableStateFlow<AddBookmarkUiState?>(null)
    val addBookmarkUiState = _addBookmarkUiState.asStateFlow()

    fun createBookmark(url: String, collectionId: Long?) {
        viewModelScope.launch {
            try {
                _addBookmarkUiState.value = AddBookmarkUiState.Loading
                bookmarksRepository.createBookmark(url, collectionId)
                _addBookmarkUiState.value = AddBookmarkUiState.Success
            } catch (exception: Exception) {
                _addBookmarkUiState.value = AddBookmarkUiState.Failure(exception)
            }
        }
    }


}

sealed interface AddBookmarkUiState {
    data object Loading : AddBookmarkUiState

    data object Success : AddBookmarkUiState

    data class Failure(val error: Exception) : AddBookmarkUiState
}
