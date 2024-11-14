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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "picture_pref")

class PicturePreference @Inject constructor(
    @ApplicationContext context: Context
) {
    private val store = context.dataStore
    private val picturesKey = stringPreferencesKey("pictureKey")

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        explicitNulls = false
    }

    val pictures: Flow<List<PictureSaveData>> = store.data.map { prefs ->
        val jsonString: String = prefs[picturesKey] ?: return@map emptyList<PictureSaveData>()
        runCatching {
            json.decodeFromString<List<PictureSaveData>>(jsonString)
        }.getOrDefault(emptyList())
    }

    suspend fun editPictures(pictureEditData: PictureSaveData) {
        val current = pictures.first()
        val newList = current.map {
            if (it.id == pictureEditData.id) pictureEditData else it
        }
        store.edit { prefs ->
            prefs[picturesKey] = json.encodeToString<List<PictureSaveData>>(newList)
        }
    }

    suspend fun addPicture(pictureSaveData: PictureSaveData) {
        val newList = pictures.first() + pictureSaveData
        store.edit { prefs ->
            prefs[picturesKey] = json.encodeToString<List<PictureSaveData>>(newList)
        }
    }

    suspend fun removePicture(pictureSaveData: PictureSaveData) {
        val current = pictures.first()
        val newList = current.filterNot { it.id == pictureSaveData.id }
        store.edit { prefs ->
            prefs[picturesKey] = json.encodeToString(newList)
        }
    }
}