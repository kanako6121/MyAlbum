package com.example.myalbum.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.top.TopScreenContent
import com.example.myalbum.feature.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TopViewModel>()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.viewModelScope.launch {
                if (uri != null) {
                    viewModel.runCatching {
                        savePhoto(uri = uri, comment = "")
                    }
                } else {
                    Timber.tag("PhotoPicker").d("No media selected")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAlbumTheme {
                MainNav()
                TopScreenContent(
                    onUpPress = { /*TODO*/ },
                    onNavigationToEditScreen = { /*TODO*/ },
                    onNavigationToPreviewScreen = {},
                    selectUri = "",
                    onClick = {pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))},
                )
            }
        }
    }
}