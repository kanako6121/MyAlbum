package com.example.myalbum.core.data

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
    currentAlbumData?.pictures?.map { comment ->
      val newPictureData = PictureData(
        id = albumId,
        uri = pictureData.uri,
        comment = comment.toString(),
      )
      val newAlbumData = AlbumData(
        id = albumId,
        title = currentAlbumData.title,
        pictures = currentAlbumData.pictures + newPictureData,
      )
      preference.updateAlubm(newAlbumData)
    }
  }

  suspend fun removePhoto(
    albumId: Int,
    pictureId: Int
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId)
    val currentPictureData = currentAlbumData?.pictures
    val updatePictureData = currentPictureData?.filterNot { it.id == pictureId }
    currentAlbumData?.let { data ->
      val updateAlbumData = updatePictureData?.let {
        data.copy(
          id = albumId,
          title = currentAlbumData.title,
          pictures = it,
        )
      }
      if (updateAlbumData != null) {
        preference.updateAlubm(updateAlbumData)
      }
    }
  }

  suspend fun deleateAlbum(albumId: Int) = withContext(Dispatchers.IO) {
    preference.removeAlbum(albumId)
  }

  private suspend fun getCurrentAlbum(albumId: Int): AlbumData? {
    return preference.albumMap.first()[albumId]
  }
}
