package com.cb.bookmrk.core.addeditcollection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.addeditcollection.navigation.AddEditCollectionArgs
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddEditCollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {

    val addEditCollectionArgs = AddEditCollectionArgs(savedStateHandle)

    fun createCollection(name: String, isPrivate: Boolean) {
        viewModelScope.launch {
            collectionsRepository.createCollection(
                name = name,
                isPrivate = isPrivate,
                groupId = addEditCollectionArgs.groupId
            )
        }
    }
}
