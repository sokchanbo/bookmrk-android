package com.cb.bookmrk.feature.editgroup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.feature.editgroup.navigation.EditGroupArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupsRepository: GroupsRepository
) : ViewModel() {

    val editGroupArgs = EditGroupArgs(savedStateHandle)

    val group: StateFlow<Group?> =
        groupsRepository.getGroup(editGroupArgs.groupId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updateGroup(title: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                groupsRepository.updateGroup(id = editGroupArgs.groupId, title = title)
            }
        }
    }
}
