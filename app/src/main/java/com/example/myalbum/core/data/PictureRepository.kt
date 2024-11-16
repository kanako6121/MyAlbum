package com.example.myalbum.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepository @Inject constructor(
  private val preference: AlbumPreference
) {
  private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

  val albums: Flow<List<AlbumData>> = preference.albumMap.map { it.values.toList() }

  fun createAlbum(title: String) {
  }

  fun updateAlbumTitle(
    albumId: Int,
    newTitle: String
  ) {
  }

  fun addPhotoToAlbum(
    albumId: Int,
    pictureData: PictureData
  ) {
  }

  fun updatePicture(
    albumId: Int,
    pictureData: PictureData
  ) {
  }

  fun removePhoto(
    albumId: Int,
    pictureId: Int
  ) {
  }

  fun deleateAlbum(albumId: Int) {
  }
}
