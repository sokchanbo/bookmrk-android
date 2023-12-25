package com.cb.bookmrk.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.GroupsRepository
import com.cb.bookmrk.core.model.data.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    groupsRepository: GroupsRepository
) : ViewModel() {

    val groups: StateFlow<List<Group>> =
        groupsRepository.getGroupsWithCollections()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
}
