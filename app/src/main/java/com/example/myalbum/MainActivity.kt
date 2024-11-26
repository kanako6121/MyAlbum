package com.example.myalbum

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.activity.viewModels
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.main.ui.MainNav
import com.example.myalbum.feature.main.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel: MainViewModel by viewModels()
  private val pickMedia =
    registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
      if (uri == null) return@registerForActivityResult

      contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
      // val nextId = viewModel.getPhotoId()
      // val pictureData = PictureData(uri = uri, id = nextId)
      // viewModel.savePhoto(pictureData)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyAlbumTheme {
        MainNav(
          mainViewModel = viewModel,
          launchPicker = {
            pickMedia.launch(
              PickVisualMediaRequest(ImageOnly)
            )
          }
        )
      }
    }
  }
}
