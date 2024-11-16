package com.example.myalbum.core.data

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.w3c.dom.Comment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepository @Inject constructor(
    private val preference: AlbumPreference
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val albums: Flow<List<AlbumData>> = preference.albumMap.map { it.values.toList() }

    fun createAlbum(title: String) {
    }

    fun updateAlbumTitle(albumId: Int,  newTitle: String ) :AlbumData {
    }

    fun addPhotoToAlbum(albumId: Int, picture: PictureData) {
    }

    fun updatePicture(albumId: Int, pictureData: PictureData) {
    }

    fun removePhoto(albumId: Int, id: Int) {
    }

    fun deleateAlbum(albumId: Int) {
    }
}
