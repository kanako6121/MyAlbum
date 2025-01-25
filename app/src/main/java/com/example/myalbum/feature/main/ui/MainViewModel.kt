package com.example.myalbum.feature.main.ui

import android.content.Context
import android.net.Uri
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  @ApplicationContext private val context: Context,
  private val repository: AlbumRepository,
) : ViewModel() {
  val uiState: StateFlow<MainUiState> = combine(
    repository.albumsFlow, repository.currentAlbumIdFlow
  ) { albums, currentAlbumId ->
    if (albums.isEmpty()) {
      createDefaultAlbum()
    }
    albums.toMainUiState(currentAlbumId)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = MainUiState.Empty,
  )

  private fun createDefaultAlbum() {
    viewModelScope.launch {
      repository.createAlbum(
        title = context.getString(R.string.default_album)
      )
    }
  }

  fun addPhoto(uri: Uri) {
    viewModelScope.launch {
      val currentAlbumId = uiState.value.currentAlbum.id
      repository.addPhotoToAlbum(currentAlbumId, uri)
    }
  }

  fun selectAlbum(albumId: Int) {
    viewModelScope.launch {
      repository.setCurrentAlbum(albumId)
    }
  }

  fun createAlbumTitle(title: String) {
    viewModelScope.launch {
      repository.createAlbum(title)
    }
  }

  fun updateAlbumTitle(albumId: Int, newTitle: String) {
    viewModelScope.launch {
      repository.updateAlbumTitle(albumId, newTitle)
    }
  }

  fun onRemovePhoto(albumId: Int, pictureId: Int) {
    viewModelScope.launch {
      repository.removePhoto(albumId, pictureId)
    }
  }

  fun deleteAlbum(albumId: Int) {
    viewModelScope.launch {
      repository.deleteAlbum(albumId)
    }
  }
}
