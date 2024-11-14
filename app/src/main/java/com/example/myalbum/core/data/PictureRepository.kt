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

    fun createAlbum(title: String): {

    }

    fun addPhotoToAlbum(albumId: Int, pictureData: PictureData): {

    }

    fun getAlbum(id: Int): AlbumData {

    }

    fun saveAlbumList(albumId: Int, updateAlbum: AlbumData): {

    }

    fun updateAlbumPictures(albumId: Int, newPictures: List<PictureData>): AlbumData {

    }

    fun removePhoto(id: Int) {

    }

    fun deleateAlbum(albumId: Int) {

    }
}