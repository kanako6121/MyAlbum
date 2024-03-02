package com.example.myalbum.core.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface StateResult<out T> {

    @Immutable
    data object Loading : StateResult<Nothing>

    @Immutable
    data object Empty : StateResult<Nothing>
}