package com.example.myalbum.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "album_pref")

class AlbumPreference @Inject constructor(
  @ApplicationContext context: Context
) {
  private val store = context.dataStore

  @OptIn(ExperimentalSerializationApi::class)
  private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
    explicitNulls = false
  }

  // 保存されているアルバムデータを、Map<アルバムID, アルバムデータ> 型でFLowで流す
  val albumMap: Flow<Map<Int, AlbumData>> = store.data.map { prefs ->
    prefs.asMap().values.mapNotNull {
      val jsonString: String = it as? String ?: return@mapNotNull null
      val albumData: AlbumData = runCatching {
        json.decodeFromString<AlbumSaveData>(jsonString).toAlbumData()
      }.getOrNull() ?: return@mapNotNull null
      albumData.id to albumData
    }.toMap()
  }

  //カレントアルバムをFLOWで流す
  val currentAlbumFlow: Flow<Int?> = store.data.map { prefs ->
    prefs[intPreferencesKey("current_album")]
  }

  // アルバムの新規作成保存
  suspend fun createAlbum(title: String) {
    val saveData = AlbumSaveData(
      id = albumMap.first().keys.maxOrNull()?.plus(1) ?: 0,
      title = title,
      pictures = emptyList()
    )
    val key = stringPreferencesKey(saveData.id.toString())
    store.edit { prefs ->
      prefs[key] = json.encodeToString<AlbumSaveData>(saveData)
    }
  }

  // 保存済みアルバムデータの更新
  suspend fun updateAlbum(albumData: AlbumData) {
    val updateAlbumData = albumData.toAlbumSaveData()
    val key = stringPreferencesKey(albumData.id.toString())
    store.edit { prefs ->
      prefs[key] = json.encodeToString<AlbumSaveData>(updateAlbumData)
    }
  }

  // 保存済みアルバムデータを削除
  suspend fun removeAlbum(albumId: Int) {
    val key = stringPreferencesKey(albumId.toString())
    store.edit { prefs ->
      prefs.remove(key)
    }
  }

  //カレントアルバムを設定
  suspend fun setCurrentAlbum(albumId: Int) {
    val key = intPreferencesKey("current_album")
    store.edit { prefs ->
      prefs[key] = albumId
    }
  }
}
