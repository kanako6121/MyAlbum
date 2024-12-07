package com.example.myalbum.feature.main.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.feature.main.data.MainUiState
import com.example.myalbum.feature.main.data.toMainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  @ApplicationContext private val context: Context,
  private val repository: AlbumRepository,
) : ViewModel() {
  val uiState: StateFlow<MainUiState> = repository.albumsFlow.mapNotNull { albums ->
    if (albums.isEmpty()) {
      createDefaultAlbum()
      null
    } else {
      albums.toMainUiState()
    }
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = MainUiState()
  )

  private fun createDefaultAlbum() {
    viewModelScope.launch {
      repository.createAlbum(
        title = context.getString(R.string.new_album)
      )
    }
  }
}
