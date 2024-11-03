package com.example.myalbum.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "albums_pref")

class AlbumPreference @Inject constructor(
    @ApplicationContext context: Context
) {
    private val store = context.dataStore
    private val albumsKey = stringPreferencesKey("albums")

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        explicitNulls = false
    }

    val albums: Flow<List<AlbumSaveData>> = store.data.map { prefs ->
        val jsonString: String = prefs[albumsKey] ?: return@map emptyList<AlbumSaveData>()
        runCatching {
            json.decodeFromString<List<AlbumSaveData>>(jsonString)
        }.getOrDefault(emptyList())
    }

    suspend fun addAlbum(albumSaveData: AlbumSaveData) {
        val newList = albums.first() + albumSaveData
        store.edit { prefs ->
            prefs[albumsKey] = json.encodeToString<List<AlbumSaveData>>(newList)
        }
    }

    suspend fun removeAlbum(albumSaveData: AlbumSaveData) {
        val current = albums.first()
        val newList = current.filterNot { it.id == albumSaveData.id }
        store.edit { prefs ->
            prefs[albumsKey] = json.encodeToString(newList)
        }
    }
}
