package com.cb.bookmrk.feature.addgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.data.repository.GroupsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository
) : ViewModel() {

    fun createGroup(title: String) {
        viewModelScope.launch {
            groupsRepository.createGroup(title)
        }
    }
}
