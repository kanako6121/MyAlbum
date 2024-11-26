package com.example.myalbum.feature.main.ui

import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: AlbumRepository,
) : ViewModel() {
}
