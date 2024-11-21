package com.example.myalbum.core.data

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.tooling.data.EmptyGroup.data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepository @Inject constructor(
  private val preference: AlbumPreference
) {
  val albums: Flow<List<AlbumData>> = preference.albumMap.map { it.values.toList() }

  suspend fun createAlbum(title: String) = withContext(Dispatchers.IO) {
    preference.createAlubm(title)
  }

  suspend fun updateAlbumTitle(
    albumId: Int,
    newTitle: String
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId)
    currentAlbumData?.let { data ->
      val newAlbumData = data.copy(title = newTitle)
      preference.updateAlubm(newAlbumData)
    }
  }

  suspend fun addPhotoToAlbum(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId)
    if (currentAlbumData?.id == albumId) {
      currentAlbumData.let { data ->
        val newAlbumData = data.copy(
          id = albumId,
          title = currentAlbumData.title,
          pictures = currentAlbumData.pictures + pictureData,
        )
        preference.updateAlubm(newAlbumData)
      }
    }
  }

  suspend fun updatePicture(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId)
    if (currentAlbumData?.id == albumId) {
      currentAlbumData.pictures.map { comment ->
        val newPictureData = PictureData(
          id = albumId,
          uri = pictureData.uri,
          comment = comment.toString(),
        )
      }
      preference.updateAlubm(
        albumData = 
      )
    }
  }

  suspend fun removePhoto(
    albumId: Int,
    pictureId: Int
  ) = withContext(Dispatchers.IO) {
    // TODO(kana) (5) ここに既存アルバムの写真リスト内の該当写真を削除して、更新したアルバムデータをAlbumPreferenceへ保存するコードを書く
  }

  suspend fun deleateAlbum(albumId: Int) = withContext(Dispatchers.IO) {
    // TODO(kana) (6) ここに既存アルバムをAlbumPreferenceから削除するコードを書く
  }

  private suspend fun getCurrentAlbum(albumId: Int): AlbumData? {
    return preference.albumMap.first()[albumId]
  }
}
