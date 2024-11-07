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

    suspend fun removePhoto(pictureData: PictureData) {
        preference.removePicture(pictureData.toPictureSaveData())
    }

    suspend fun addPictureToAlbum(albumId: Int, pictureData: PictureData) {
        val currentAlbum = albums.value.lastOrNull()
        val newAlbum = currentAlbum?.pictures?.lastOrNull(
        )
        preference.addAlbum(currentAlbum.map { it.toAlbumSaveData() }
    }

    val albums: StateFlow<List<AlbumData>> = preference.albums.map { saveList -> saveList.map { it.toAlbumData() }
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
