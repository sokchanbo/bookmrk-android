package com.cb.bookmrk.feature.editgroup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.CollectionsRepository
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.feature.editgroup.navigation.EditGroupArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupsRepository: GroupsRepository,
    collectionsRepository: CollectionsRepository
) : ViewModel() {

    val editGroupArgs = EditGroupArgs(savedStateHandle)

    val group: StateFlow<Group?> =
        groupsRepository.getGroup(editGroupArgs.groupId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val collectionCount: StateFlow<Int> =
        collectionsRepository.countCollectionWithinGroup(editGroupArgs.groupId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    private val _showDeleteGroupError = MutableStateFlow<Boolean>(false)
    val showDeleteGroupError = _showDeleteGroupError.asStateFlow()

    fun updateGroup(title: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                groupsRepository.updateGroup(id = editGroupArgs.groupId, title = title)
            }
        }
    }

    fun removeGroup() {
        viewModelScope.launch {
            if (collectionCount.value > 0) {
                _showDeleteGroupError.value = true
            } else {
                groupsRepository.deleteGroup(editGroupArgs.groupId)
            }
        }
    }
}
