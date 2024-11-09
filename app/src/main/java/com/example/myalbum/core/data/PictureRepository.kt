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

    suspend fun addPicture(pictureData: PictureData) {
        preference.addPicture(pictureData.toPictureSaveData())
    }

    suspend fun updatePicture(pictureData: PictureData) {
        preference.editPictures(pictureData.toPictureSaveData())
    }

    fun updateAlbum(updatedAlbum: AlbumData) {
        val currentAlbums = albums.value
        val albumId = currentAlbums.map { it.id }
        preference.addAlbum(listOf(updatedAlbum.pictures))
    }

    //現状のアルバム一覧をまず取得する
    //その一覧から該当のIDのアルバムデータを取得する
    //アルバムデータ内の写真リストに、写真を保存する（これはいままで１つのアルバムでやったのと同じ）
    //３で更新したアルバムデータを、アルバム一覧内の該当IDのアルバム部分を置き換える
    //更新されたアルバム一覧を保存する。

    suspend fun removePhoto(pictureData: PictureData) {
        preference.removePicture(pictureData.toPictureSaveData())
    }

    val albums: StateFlow<List<AlbumData>> = preference.albums.map { saveList ->
        saveList.map { it.toAlbumData() }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    suspend fun addAlbum(albumData: AlbumData) {
        preference.addAlbum(albumData.toAlbumSaveData())
    }

    suspend fun removeAlbum(albumId: Int) {
        preference.removeAlbum(albumId)
    }
}
