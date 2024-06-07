package com.example.myalbum.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _selectedPhoto = MutableStateFlow<PictureData?>(value = null)
    val selectePhoto: StateFlow<PictureData?>
        get() = _selectedPhoto

    fun updateSelectPhoto(name: String) {
        viewModelScope.launch {
            val selectedPhoto: PictureData? = repository.getPhotoUri(name)
            if (selectedPhoto != null) {
                _selectedPhoto.value = selectedPhoto
            }
        }
    }

    fun getPictureData(selectedId: Int): PictureData?  {
        val data = pictures.value.firstOrNull { if.id == id}
        return data
    }
}