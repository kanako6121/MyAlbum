package com.example.myalbum.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val preference: AlbumPreference
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob()

    val albums: StateFlow<List<AlbumData>> = .al { saveList ->
        saveList.map { it.toAlbumData() }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    suspend fun addAlbum(id: Int, title: String, albumData: List<PictureData>) {
        preference.addAlbum(id, title, albumData)
    }

    suspend fun removeAlbum(albumData: AlbumData) {
        preference.removeAlbums(albumData.toAlbumSaveData())
    }
}