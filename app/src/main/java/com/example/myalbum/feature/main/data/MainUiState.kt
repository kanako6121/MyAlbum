package com.example.myalbum.feature.main.data

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.example.myalbum.core.data.AlbumData

@Immutable
data class MainUiState(
  val albumMenus: List<AlbumMenu> = emptyList(),
  val errorMessage: String? = null,
)

@Immutable
data class AlbumMenu(
  val id: Int,
  val uri: Uri,
  val title: String,
)

fun AlbumData.toAlbumMenu() =
  AlbumMenu(
    id = id,
    // 最初の写真がよければ first、追加した最新の写真がよければ last
    uri = pictures.firstOrNull()?.uri ?: Uri.EMPTY,
    title = title
  )

fun List<AlbumData>.toMainUiState() =
  MainUiState(
    albumMenus = map { it.toAlbumMenu() }
  )
