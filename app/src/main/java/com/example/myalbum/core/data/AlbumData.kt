package com.example.myalbum.core.data

data class AlbumData (
    val id: Int,
    val title: String,
    val pictures: List<PictureData>
)