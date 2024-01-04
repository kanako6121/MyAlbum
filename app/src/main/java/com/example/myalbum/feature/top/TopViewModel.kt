package com.example.myalbum.feature.top

import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
  private val repository: PhotoRepository,
) : ViewModel() {
}
