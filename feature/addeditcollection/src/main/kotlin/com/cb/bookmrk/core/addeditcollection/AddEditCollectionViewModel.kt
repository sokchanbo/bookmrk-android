package com.cb.bookmrk.core.addeditcollection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.addeditcollection.navigation.AddEditCollectionArgs
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddEditCollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val collectionsRepository: CollectionsRepository,
    groupsRepository: GroupsRepository
) : ViewModel() {

    val addEditCollectionArgs = AddEditCollectionArgs(savedStateHandle)

    val collection: StateFlow<Collection?> =
        if (addEditCollectionArgs.collectionId == null) {
            emptyFlow()
        } else {
            collectionsRepository.getCollectionWithGroup(addEditCollectionArgs.collectionId)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    val groups: StateFlow<List<Group>> =
        if (addEditCollectionArgs.collectionId == null) {
            emptyFlow()
        } else {
            groupsRepository.getGroups()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    fun createCollection(name: String, isPrivate: Boolean) {
        viewModelScope.launch {
            collectionsRepository.createCollection(
                name = name,
                isPrivate = isPrivate,
                groupId = addEditCollectionArgs.groupId!!
            )
        }
    }

    fun updateCollection(name: String, groupId: Long) {
        viewModelScope.launch {
            collectionsRepository.updateCollection(
                id = addEditCollectionArgs.collectionId!!,
                name = name,
                groupId = groupId
            )
        }
    }
}
