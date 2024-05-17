package com.example.myalbum.feature.top

import android.net.Uri
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.data.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopViewModel @Inject constructor(
    private val repository: PictureRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _pickedPhoto = MutableStateFlow(PictureData(uri = Uri.EMPTY, comment = null))
    private val pickedPhoto: StateFlow<PictureData> = _pickedPhoto.asStateFlow()
    private val photoUri: Uri = checkNotNull(savedStateHandle["photouri"])

    suspend fun savePhoto(photo: PictureData) {
        viewModelScope.launch {
            val photos = pickedPhoto.value.copy(
                uri = photoUri,
                comment = null,
            )
            _pickedPhoto.emit(photos)
            runCatching {
                repository.addPicture(photo)
            }
        }
        loadUri(photoUri)
    }

    fun loadUri(photoUri: Uri? = null) {
        viewModelScope.launch {
            repository.pictures
        }
    }
}