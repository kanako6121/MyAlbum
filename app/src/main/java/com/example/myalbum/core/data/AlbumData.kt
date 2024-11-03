package com.example.myalbum.core.data

import kotlinx.serialization.Serializable


data class AlbumData (
    val id: Int,
    val title: String,
    val pictures: List<PictureData>
)

@Serializable
data class AlbumSaveData(
    val id: Int,
    val title: String,
    val pictures: List<PictureSaveData>
)