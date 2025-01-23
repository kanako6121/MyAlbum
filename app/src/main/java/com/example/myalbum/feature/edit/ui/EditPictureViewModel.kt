package com.example.myalbum.feature.edit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.feature.edit.data.EditPictureUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPictureViewModel @Inject constructor(
  private val repository: AlbumRepository,
) : ViewModel() {

  private val editPictureFlow = MutableStateFlow<Pair<Int, Int>?>(null)
  val uiState: StateFlow<EditPictureUiState> = combine(
    repository.albumsFlow,
    editPictureFlow,
  ) { albums, edit ->
    if (edit == null) return@combine EditPictureUiState()

    val albumId = edit.first
    val pictureId = edit.second
    val album = albums.find { it.id == albumId }
    val pictureData = album?.pictures?.find { it.id == pictureId }

    EditPictureUiState(
      isLoading = false,
      pictureData = pictureData,
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = EditPictureUiState(),
  )

  fun setEditPicture(albumId: Int, pictureId: Int) {
    editPictureFlow.value = albumId to pictureId
  }

  fun savePicture(comment: String) {
    viewModelScope.launch {
      val pictureData = uiState.value.pictureData
      val newPictureData = pictureData?.copy(comment = comment)
      if (newPictureData != null) {
        editPictureFlow.value?.let { repository.updatePicture(it.first, newPictureData) }
      }
    }
  }
}
