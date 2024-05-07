package com.example.myalbum.main

sealed class MainIntent {
    object FetchPhotos: MainIntent()
}