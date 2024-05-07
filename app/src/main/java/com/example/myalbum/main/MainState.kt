package com.example.myalbum.main

sealed class MainState {

    object Idle: MainState()
    object Loading: MainState()
    data class Photos(val photos: List<Photos>): MainState()
    data class Error(val error: String?): MainState()
}