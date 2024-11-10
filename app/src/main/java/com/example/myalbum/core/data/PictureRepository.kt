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

    private fun updateAlbums(updatedAlbums: List<AlbumData>) {
        preference.updateAlbums(updatedAlbums.map { it.toAlbumSaveData() })
    }

    fun updateAlbumPictures(albumId: Int, newPictures: List<PictureData>) {
        val currentAlbums = albums.value.toMutableList()
        val albumId = currentAlbums.indexOfFirst { it.id == albumId }
        if( albumId !=null ) {
            val updateAlbums = currentAlbums[albumId].copy(
                pictures = newPictures
            )
            currentAlbums[albumId] = updateAlbums
            updateAlbums(currentAlbums)
        }
    }

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
