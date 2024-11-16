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
  }

  suspend fun updateAlbumTitle(
    albumId: Int,
    newTitle: String
  ) = withContext(Dispatchers.IO) {
  }

  suspend fun addPhotoToAlbum(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
  }

  suspend fun updatePicture(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
  }

  suspend fun removePhoto(
    albumId: Int,
    pictureId: Int
  ) = withContext(Dispatchers.IO) {
  }

  suspend fun deleateAlbum(albumId: Int) = withContext(Dispatchers.IO) {
  }

  private suspend fun getCurrentAlbum(albumId: Int): AlbumData? {
    return preference.albumMap.first()[albumId]
  }
}
