package com.cb.bookmrk.feature.addbookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.BookmarksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddBookmarkViewModel @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    private val _addBookmarkUiState = MutableStateFlow<AddBookmarkUiState?>(null)
    val addBookmarkUiState = _addBookmarkUiState.asStateFlow()

    fun createBookmark(url: String) {
        viewModelScope.launch {
            try {
                _addBookmarkUiState.value = AddBookmarkUiState.Loading
                bookmarksRepository.createBookmark(url)
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
