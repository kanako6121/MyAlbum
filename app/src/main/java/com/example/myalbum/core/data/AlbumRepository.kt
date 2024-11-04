package com.example.myalbum.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val preference: AlbumPreference
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val albums: StateFlow<List<AlbumData>> = preference.albums.map { saveList ->
        saveList.map { it.toAlbumData() }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    suspend fun addAlbum(albumData: AlbumData) {
        preference.addAlbum(albumData.toAlbumSaveData())
    }

    suspend fun removeAlbum(albumData: AlbumData) {
        preference.removeAlbum(albumData.toAlbumSaveData())
    }
}