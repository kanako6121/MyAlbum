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

    suspend fun savePhoto(uri: Uri, comment: String) {
        viewModelScope.launch {
            val photoUri = repository.pictures
            photoUri.runCatching {
                
            }
        }
    }
}