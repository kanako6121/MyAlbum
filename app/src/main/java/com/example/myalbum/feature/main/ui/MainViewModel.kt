package com.example.myalbum.feature.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.feature.main.data.MainUiState
import com.example.myalbum.feature.main.data.toMainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: AlbumRepository,
) : ViewModel() {
  val uiState: StateFlow<MainUiState> = repository.albumsFlow.map { albums ->
    albums.toMainUiState()
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = MainUiState()
  )
}
