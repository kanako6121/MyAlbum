package com.example.myalbum.feature.top

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TopViewModel @Inject constructor(
    private val repository: PictureRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _pickedPhoto = MutableStateFlow(PictureData(uri = Uri.EMPTY))
    val pickedPhoto: StateFlow<PictureData> = _pickedPhoto.asStateFlow()

    suspend fun savePhoto(photos: PictureData) {
        val photoUri = _pickedPhoto.value.copy()
        _pickedPhoto.emit(photoUri)
        runCatching {
            repository.pictures
        }
    }
}