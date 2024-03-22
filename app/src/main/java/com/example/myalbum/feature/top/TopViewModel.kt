package com.example.myalbum.feature.top

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopViewModel : ViewModel() {
    private val _pickedImageUri = MutableLiveData<Uri?>()
    val pickedImageUri: LiveData<Uri?> = _pickedImageUri
    fun onPhotoSelected(uri: Uri) {
        _pickedImageUri.value = uri
    }
}