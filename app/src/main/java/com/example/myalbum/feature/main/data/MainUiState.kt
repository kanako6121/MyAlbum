package com.example.myalbum.feature.main.data

sealed class MainUiState {

  object Idle : MainUiState()
  object Loading : MainUiState()
  data class Error(val error: String?) : MainUiState()
}
