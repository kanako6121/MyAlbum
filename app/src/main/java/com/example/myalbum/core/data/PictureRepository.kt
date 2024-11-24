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
    val currentAlbumData = getCurrentAlbum(albumId) ?: return@withContext
    currentAlbumData.let { data ->
      val newAlbumData = data.copy(title = newTitle)
      preference.updateAlubm(newAlbumData)
    }
  }

  suspend fun addPhotoToAlbum(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId) ?: return@withContext
    val newAlbumData = currentAlbumData.copy(
      pictures = currentAlbumData.pictures + pictureData
    )
    preference.updateAlubm(newAlbumData)
  }

  suspend fun updatePicture(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId) ?: return@withContext
    val updatePictures = currentAlbumData.pictures + pictureData
    val newAlbumData = currentAlbumData.copy(
      pictures = updatePictures
    )
    preference.updateAlubm(newAlbumData)
  }

  suspend fun removePhoto(
    albumId: Int,
    pictureId: Int
  ) = withContext(Dispatchers.IO) {
    val currentAlbumData = getCurrentAlbum(albumId) ?: return@withContext
    val updatePictureData = currentAlbumData.pictures.filterNot { it.id == pictureId }
    val updateAlbumData = currentAlbumData.copy(pictures = updatePictureData)
    preference.updateAlubm(updateAlbumData)
  }

  suspend fun deleateAlbum(albumId: Int) = withContext(Dispatchers.IO) {
    preference.removeAlbum(albumId)
  }

  private suspend fun getCurrentAlbum(albumId: Int): AlbumData? {
    return preference.albumMap.first()[albumId]
  }
}
