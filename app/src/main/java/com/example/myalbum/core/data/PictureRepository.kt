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
    // TODO(kana) (1) ここに新規アルバムデータをAlbumPreferenceへ保存するコードを書く
  }

  suspend fun updateAlbumTitle(
    albumId: Int,
    newTitle: String
  ) = withContext(Dispatchers.IO) {
    // TODO(kana) (2) ここに既存アルバムデータのタイトルを更新して、更新したアルバムデータをAlbumPreferenceへ保存するコードを書く
  }

  suspend fun addPhotoToAlbum(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    // TODO(kana) (3) ここに既存アルバムの写真リストに写真を追加して、更新したアルバムデータをAlbumPreferenceへ保存するコードを書く
  }

  suspend fun updatePicture(
    albumId: Int,
    pictureData: PictureData
  ) = withContext(Dispatchers.IO) {
    // TODO(kana) (4) ここに既存アルバムの写真リスト内の該当写真を部分を更新して、更新したアルバムデータをAlbumPreferenceへ保存するコードを書く
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
