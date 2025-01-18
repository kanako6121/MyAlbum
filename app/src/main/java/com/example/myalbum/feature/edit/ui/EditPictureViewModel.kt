package com.example.myalbum.feature.edit.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditPictureViewModel @Inject constructor(
  private val albumRepository: AlbumRepository,

  savedStateHandle: SavedStateHandle,
) : ViewModel() {

}
