package com.example.myalbum.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepository @Inject constructor(
    private val preference: PicturePreference
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val albums: StateFlow<List<AlbumData>> = preference.albums.map { saveList ->
        saveList.map { it.toAlbumData() }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    suspend fun addPicture(pictureData: PictureData) {
        preference.addPicture(pictureData.toPictureSaveData())
    }

    suspend fun updatePicture(pictureData: PictureData) {
        preference.editPictures(pictureData.toPictureSaveData())
    }

    suspend fun updateAlbums(updatedAlbums: List<AlbumData>) {
        preference.updateAlbums(updatedAlbums.map { it.toAlbumSaveData() })
    }

    suspend fun addPictureToAlbum(albumId: Int, pictureData: PictureData) {
        val currentAlbums = albums.value.toMutableList()
        val albumIndex = currentAlbums.indexOfFirst { it.id == albumId }

        if (albumIndex != -1) {
            val updatedAlbum = currentAlbums[albumIndex].copy(
                pictures = currentAlbums[albumIndex].pictures + pictureData
            )
            currentAlbums[albumIndex] = updatedAlbum
            updateAlbums(currentAlbums)
        }
    }
    //現状のアルバム一覧をまず取得する
    //その一覧から該当のIDのアルバムデータを取得する
    //アルバムデータ内の写真リストに、写真を保存する（これはいままで１つのアルバムでやったのと同じ）
    //３で更新したアルバムデータを、アルバム一覧内の該当IDのアルバム部分を置き換える

    suspend fun removePhoto(pictureData: PictureData) {
        preference.removePicture(pictureData.toPictureSaveData())
    }

    suspend fun addAlbum(albumData: AlbumData) {
        preference.addAlbum(albumData.toAlbumSaveData())
    }

    suspend fun removeAlbum(albumId: Int) {
        preference.removeAlbum(albumId)
    }
}
