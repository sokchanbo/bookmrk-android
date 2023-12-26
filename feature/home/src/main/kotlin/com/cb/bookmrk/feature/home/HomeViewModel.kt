package com.cb.bookmrk.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.bookmrk.core.domain.GetHomeScreenDataUseCase
import com.cb.bookmrk.core.model.data.HomeScreenData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeScreenData: GetHomeScreenDataUseCase
) : ViewModel() {

    val homeScreenData: StateFlow<HomeScreenData?> =
        getHomeScreenData()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null
            )
}
