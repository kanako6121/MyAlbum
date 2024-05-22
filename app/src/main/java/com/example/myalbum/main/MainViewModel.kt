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
}