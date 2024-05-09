package com.example.myalbum.feature.top

import android.net.Uri
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

    suspend fun savePhoto(uri: Uri, comment: String) {
        viewModelScope.launch {
            val photoUri = pickedPhoto.value.copy(
                uri = uri,
                comment = comment
            )
            _pickedPhoto.emit(photoUri)
            runCatching {
                repository.addPicture(photoUri)
            }
        }
    }
}