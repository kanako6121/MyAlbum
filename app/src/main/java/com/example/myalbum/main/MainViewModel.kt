package com.example.myalbum.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PictureRepository,
) : ViewModel() {
    val pictures: StateFlow<List<PictureData>> = repository.pictures
    fun savePhoto(photo: PictureData) {
        viewModelScope.launch {
            runCatching {
                repository.addPicture(photo)
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
            repository.updatePicture(pictureData)
        }
    }

    fun removePhoto(pictureData: PictureData) {
        viewModelScope.launch {
            repository.removePhoto(pictureData)
        }
    }
}
