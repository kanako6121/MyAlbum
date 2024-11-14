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

    fun createAlbum(title: String, pictures: List<PictureData>): AlbumData{
        //デフォルトのアルバムに名前をつけて、識別IDを付与して写真リストとセットになったアルバムデータを返す。
        //そのidが保存用keyにもなる
    }

    fun updateAlbum(albumId: Int, currentPictures: List<PictureData>, newPictures: List<PictureData>): AlbumData {
      //Idによって識別できているアルバムデータをTopScreenに呼び出すとき、書き換える。
    }

    fun addPhotoToAlbum(albumId: Int, pictureData: PictureData): {
        //当該アルバムに写真を追加する
    }

    fun editAlbumData(albumId: Int, picture: PictureData): {
        //当該アルバムのPictureData(idとコメントuriのどれかまたは全部）を編集する
    }

    fun saveAlbumList(albumId: Int, updateAlbum: AlbumData): {
        //アルバムデータをアルバムごとに保存する
    }


    fun removePhoto(id: Int) {
        //写真を削除
    }

    fun deleateAlbum(albumId: Int) {
        //アルバムを削除
    }
}