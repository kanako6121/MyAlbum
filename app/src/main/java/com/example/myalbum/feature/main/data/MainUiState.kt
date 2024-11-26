package com.example.myalbum.feature.main.data

import android.net.Uri

data class MainUiState(
  val albumMenus: List<AlbumMenu> = emptyList(),
  val errorMessage: String? = null,
)

data class AlbumMenu(
  val id: Int,
  val ure: Uri,
  val title: String,
)
