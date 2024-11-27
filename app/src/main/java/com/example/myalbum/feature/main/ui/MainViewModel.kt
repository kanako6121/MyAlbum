package com.example.myalbum.feature.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.feature.main.data.AlbumMenu
import com.example.myalbum.feature.main.data.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private  val repository: AlbumRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(MainUiState())
  val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
  private val albumData: Flow<List<AlbumData>> = repository.albums

  init {
    viewModelScope.launch {
      repository.albums.collect{
        
        }
      }
    }
  }
}
