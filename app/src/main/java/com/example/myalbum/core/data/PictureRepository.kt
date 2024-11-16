package com.example.myalbum.core.data

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.w3c.dom.Comment
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

    fun createAlbum(title: String): AlbumData{
//（１）ここでIDは自動追加
    }

    fun updateAlbumTitle(albumId: Int,  newTitle: String ) {
    }
//（２）そのIDのタイトルを新しいタイトルにアップデート

    fun addPhotoToAlbum(albumId: Int, picture: PictureData): {
    }
//（３）写真１枚追加

    fun updatePicture(albumId: Int, picture: List<PictureData>): AlbumData{
    }
//（４）その指定のアルバムの写真を１枚更新（コメント更新）

    fun removePhoto(id: Int) {
        //（５）
    }

    fun deleateAlbum(albumId: Int) {
        //（６）
    }
}