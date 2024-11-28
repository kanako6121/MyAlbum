package com.example.myalbum.feature.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.AlbumRepository
import com.example.myalbum.feature.main.data.AlbumMenu
import com.example.myalbum.feature.main.data.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: AlbumRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(MainUiState())
  val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      repository.albums.collect { menuList ->
        val albumMenus = menuList.map { menu ->
          AlbumMenu(
            id = menu.id,
            uri = menu.pictures.first().uri,
            title = menu.title,
          )
        }
        _uiState.value = _uiState.value.copy(
          albumMenu = albumMenus,
          errorMessage = null,
        )
      }
    }
  }
}
