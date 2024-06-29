package com.example.myalbum.core.data

import android.net.Uri
import kotlinx.serialization.Serializable

data class PictureData(
    val id: Int,
    val uri: Uri,
    val comment: String? = null,
)

@Serializable
data class PictureSaveData(
    val id: Int,
    val uriString: String,
    val comment: String? = null,
)

fun PictureData.toPictureSaveData() =
    PictureSaveData(
        id = id,
        uriString = uri.toString(),
        comment = comment,
    )

fun PictureSaveData.toPictureData() =
    PictureData(
        id = id,
        uri = Uri.parse(uriString),
        comment = comment,
    )
