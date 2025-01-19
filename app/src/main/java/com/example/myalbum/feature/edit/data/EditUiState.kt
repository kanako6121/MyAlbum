package com.example.myalbum.feature.edit.data

import com.example.myalbum.core.data.PictureData

data class EditPictureUiState(
  val isLoading: Boolean = true,
  val pictureData: PictureData? = null,
  )
