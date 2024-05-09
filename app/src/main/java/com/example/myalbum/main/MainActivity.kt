package com.example.myalbum.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.top.TopScreenContent
import com.example.myalbum.feature.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TopViewModel>()
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    val mimeType = "image/gif"
    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.SingleMimeType(mimeType)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
            MyAlbumTheme {
                MainNav()
                TopScreenContent(
                    onUpPress = { /*TODO*/ },
                    onNavigationToEditScreen = { /*TODO*/ },
                    onNavigationToPreviewScreen = {},
                    onClick = {},
                )
            }
        }
    }
}