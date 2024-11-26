package com.example.myalbum.feature.main.data

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
  val albumMenu: List<AlbumMenu> = emptyList(),
  val errorMessage: String? = null,
)

@Immutable
data class AlbumMenu(
  val id: Int,
  val uri: Uri,
  val title: String,
)
