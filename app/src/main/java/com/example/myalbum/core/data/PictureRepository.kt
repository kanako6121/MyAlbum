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

    fun createAlbum(title: String):{
//（１）
    }

    fun updateAlbumTitle(albumId: Int,  newTitle: String ) ：AlbumData{
    }
//（２）新しいタイトルにアップデート（ここでタイトルと一緒に固有のID付与

    fun addPhotoToAlbum(pictures: List<PictureData>): AlbumData{
    }
//（３）写真１枚追加　該当のアルバム（ID）に、クリックした写真（id.uri）のPictureData（コメントまだなし）を追加

    fun updatePicture(id: Int, comment: String): PictureData{
    }
//（４）その指定のアルバムの写真を１枚更新（コメント更新）

    fun removePhoto(id: Int) {
        //（５）
    }

    fun deleateAlbum(albumId: Int) {
        //（６）
    }
}