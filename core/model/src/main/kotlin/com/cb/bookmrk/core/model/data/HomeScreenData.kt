package com.cb.bookmrk.core.model.data

data class HomeScreenData(
    val allBookmarkCount: Int,
    val unsortedBookmarkCount: Int,
    val trashBookmarkCount: Int,
    val groups: List<Group>
)
