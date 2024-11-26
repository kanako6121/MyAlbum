package com.example.myalbum.feature.top.ui

import androidx.lifecycle.ViewModel
import com.example.myalbum.core.data.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
  private val repository: AlbumRepository,
): ViewModel() {
}
