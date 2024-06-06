package com.example.myalbum.core.data

import android.net.Uri
import kotlinx.serialization.Serializable

data class PictureData(
    val uri: Uri,
    val comment: String? = null,
)

@Serializable
data class PictureSaveData(
    val uriString: String,
    val comment: String? = null,
)

fun PictureData.toPictureSaveData() =
    PictureSaveData(
        uriString = uri.toString(),
        comment = comment,
    )

fun PictureSaveData.toPictureData() =
    PictureData(
        uri = Uri.parse(uriString),
        comment = comment,
    )

@Serializable
data class PictureEditData(
    val uriString: String,
    val comment: String? = null,
)

fun PictureData.toPictureEditData() =
    PictureEditData(
        uriString = uri.toString(),
        comment = comment,
    )

fun PictureEditData.toPictureData() =
    PictureData(
        uri = Uri.parse(uriString),
        comment = comment,
    )
