package com.example.myalbum.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    val pictures: StateFlow<List<PictureData>> = pictureRepository.pictures
    val albums: StateFlow<List<AlbumData>> = albumRepository.albums
    
    fun savePhoto(photo: PictureData) {
        viewModelScope.launch {
            runCatching {
                pictureRepository.addPicture(photo)
            }
        }
    }

    fun getPhotoId(): Int {
        val lastId = pictures.value.lastOrNull()?.id
        return if (lastId == null) 0 else lastId + 1
    }

    fun getPictureData(selectedId: Int): PictureData? {
        val data = pictures.value.firstOrNull { it.id == selectedId }
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

    fun addAlbums(albums: AlbumData) {
        viewModelScope.launch {
            runCatching {
                albumRepository.addAlbum(albums)
            }
        }
    }

    fun repoveAlbums(albums: AlbumData) {
        viewModelScope.launch {
            albumRepository.removeAlbum(albums)
        }
    }
}
