package com.example.myalbum.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.viewModels
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.theme.MyAlbumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri == null) return@registerForActivityResult

        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pictureData = PictureData(uri = uri)
        viewModel.savePhoto(pictureData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAlbumTheme {
                MainNav(
                  mainViewModel = viewModel,
                  launchPicker = {
                      pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                  }
                )
            }
        }
    }
}
