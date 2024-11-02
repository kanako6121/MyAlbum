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
    val albums: List<AlbumSaveData<PictureData>?,
)

fun AlbumData.toAlbumSaveData() =
    AlbumSaveData(
        id = id,
        title = title,
        albums = ,
    )

fun AlbumSaveData.toAlbumData() =
    AlbumData(
        id = id,
        title = title,
        pictures = ,
    )
