package com.example.myalbum.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {
    val pictures: StateFlow<List<PictureData>> = pictureRepository.pictures
    private val _currentPictures = MutableStateFlow<List<PictureData>>(emptyList())
    val currentPictures: StateFlow<List<PictureData>> = _currentPictures
    val albums: StateFlow<List<AlbumData>> = pictureRepository.albums


    fun savePhoto(photo: PictureData) {
        viewModelScope.launch {
            runCatching {
                pictureRepository.addPicture(photo)
            }
        }
    }

    fun getPhotoId(): Int {
        val lastId = albums.value.lastOrNull()?.id
        return if (lastId == null) 0 else lastId + 1
    }

    fun getPictureData(selectedId: Int): AlbumData? {
        val data = albums.value.firstOrNull { it.id == selectedId }
        return data
    }

    fun saveEditPhoto(pictureData: PictureData) {
        viewModelScope.launch {
            pictureRepository.updatePicture(pictureData)
        }
    }

    fun removePhoto(pictureData: PictureData) {
        viewModelScope.launch {
            pictureRepository.removePhoto(pictureData)
        }
    }

    fun getAlbumId(): Int {
        val lastId = albums.value.lastOrNull()?.id ?: 0
        return lastId + 1
    }

    fun addNewAlbum(title: String) {
        viewModelScope.launch {
            val newAlbum = AlbumData(
                id = getAlbumId(),
                title = title,
                pictures = _currentPictures.value
            )
            pictureRepository.addAlbum(newAlbum)
            resetScreen()
        }
    }

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            val album = albums.value.firstOrNull { it.id == albumId }
            if (album != null) {
                _currentPictures.value = album.pictures
            }
        }
    }

    fun updateNewAlbum(albumId: Int, newPicture: PictureData) {
        viewModelScope.launch {
            val album = albums.value.firstOrNull {it.id == albumId }
            if(album != null) {
                val newPictures = album.pictures + newPicture
                pictureRepository.updateAlbumPictures(albumId, newPictures)
            }
        }
    }

    fun updatedAlbums(albumId: Int, updatePicture: PictureData) {
        viewModelScope.launch {
            val album = albums.value.firstOrNull { it.id == albumId}
            if(album != null) {
                val updatePictures = album.pictures.map {
                    if(it.id == updatePicture.id) updatePicture else it
                }
                pictureRepository.updateAlbumPictures(albumId, updatePictures)
        }
        }
    }


    fun removeAlbums(albumsId: Int) {
        viewModelScope.launch {
            pictureRepository.removeAlbum(albumsId)
        }
    }

    private fun resetScreen() {
        _currentPictures.value = emptyList()
    }
}
