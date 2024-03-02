package com.example.myalbum.feature.top

import com.example.myalbum.core.data.StateResult

data class TopScreenUiState(
    val isUploadState: StateResult<Unit> = StateResult.Empty,
    val errorMessage: String? = null,
)
