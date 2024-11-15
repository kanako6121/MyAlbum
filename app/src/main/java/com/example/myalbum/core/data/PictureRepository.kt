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
    val pictures: StateFlow<List<PictureData>> = preference.pictures.map { saveList ->
        saveList.map { it.toPictureData() }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )

    suspend fun addPicture(pictureData: PictureData) {
        preference.addPicture(pictureData.toPictureSaveData())
    }

    suspend fun updatePicture(pictureData: PictureData) {
        preference.editPictures(pictureData.toPictureSaveData())
    }

    suspend fun removePhoto(pictureData: PictureData) {
        preference.removePicture(pictureData.toPictureSaveData())
    }

    fun createAlbum(title: String): AlbumData {
//デフォルトアルバム　idは＋で
    }

    fun updateAlbumTitle(albumId: Int,  newTitle: String ) {
    }
//別名のタイトルにするとき

    fun addPhotoToAlbum(albumId: Int, pictureData: PictureData): {
    }
//指定のアルバムに新しい写真を追加

    fun updatePicture(pidtureId: Int, picture: PictureData): {
    }
//指定のアルバムのコメントや写真を更新

    fun getAlbumId(albumId: Int): AlbumData {

    }

    fun getAlbumData(): List<AlbumData> {

    }


    fun removePhoto(id: Int) {
        //写真を削除
    }

    fun deleateAlbum(albumId: Int) {
        //アルバムを削除
    }
}