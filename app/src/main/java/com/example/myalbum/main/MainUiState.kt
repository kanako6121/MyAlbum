package com.example.myalbum.main

import androidx.compose.runtime.Stable
import com.example.myalbum.core.data.StateResult

@Stable
data class MainUiState (
    val readyState: StateResult<Unit> = StateResult.Loading,
    val loadingState: Boolean = false,
)