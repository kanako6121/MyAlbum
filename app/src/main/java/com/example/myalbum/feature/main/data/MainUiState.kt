package com.example.myalbum.feature.main.data

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.example.myalbum.core.data.AlbumData

@Immutable
data class MainUiState(
  val albumMenus: List<AlbumMenu>,
  val errorMessage: String,
  var currentAlbum: AlbumData,
) {
  companion object {
    val Empty = MainUiState(
      albumMenus = emptyList(),
      errorMessage = "",
      currentAlbum = AlbumData(id = 0, title = "FavPic", pictures = emptyList())
    )
  }
}

@Immutable
data class AlbumMenu(
    val id: Int,
    val uri: Uri,
    var title: String,
)

fun AlbumData.toAlbumMenu() =
  AlbumMenu(
    id = id,
    uri = pictures.firstOrNull()?.uri ?: Uri.EMPTY,
    title = title,
  )

fun List<AlbumData>.toMainUiState(albumId: Int) =
  MainUiState(
    albumMenus = map { it.toAlbumMenu() },
    errorMessage = "",
    currentAlbum = firstOrNull { it.id == albumId } ?: AlbumData(id = 0, title = "FavPic", pictures = emptyList())
  )
