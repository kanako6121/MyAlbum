package com.example.myalbum.feature.top

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
  private val repository: PhotoRepository,
  ) : ViewModel() {
  private val _uiState = MutableStateFlow(TopScreenUiState())
  val uiState: StateFlow<TopScreenUiState> = _uiState.asStateFlow()

  fun getImageUri(imageId: Long): Uri {
    return ContentUris.withAppendedId(
      MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
      imageId
    )
  }

  fun getImages(context: Context): List<Uri> {
    val images = mutableListOf<Uri>()
    val projection = arrayOf(
      MediaStore.Images.Media._ID,
      MediaStore.Images.Media.DISPLAY_NAME,
      MediaStore.Images.Media.DATE_ADDED
    )
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
    val query = context.contentResolver.query(
      MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
      projection,
      null,
      null,
      sortOrder
    )

    query?.use { cursor ->
      val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

      while (cursor.moveToNext()) {
        val imageId = cursor.getLong(idColumn)
        val imageUri = getImageUri(imageId)
        images.add(imageUri)
      }
    }
    return images
  }
}