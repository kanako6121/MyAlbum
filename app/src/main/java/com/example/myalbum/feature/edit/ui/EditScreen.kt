package com.example.myalbum.feature.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myalbum.R
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.feature.main.ui.MainViewModel

@Composable
fun EditScreen(
  selectedId: Int,
  viewModel: MainViewModel = hiltViewModel(),
  selectedPictureData: (Int, PictureData) -> Unit,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var comment by remember { mutableStateOf(uiState.currentAlbum.pictures.find { it.id == selectedId }?.comment ?: "") }
  val pictureData = uiState.currentAlbum.pictures.find { it.id == selectedId }
  val maxChar = 10
  Column(
    modifier = Modifier
      .fillMaxWidth()
  ) {
    AsyncImage(
      modifier = Modifier
          .aspectRatio(1f)
          .padding(16.dp)
          .fillMaxWidth(),
      model = pictureData?.uri,
      contentDescription = null
    )
    OutlinedTextField(
      modifier = Modifier
          .padding(8.dp)
          .align(Alignment.CenterHorizontally),
      value = comment,
      onValueChange = { newComment ->
        if (newComment.length <= maxChar) {
          comment = newComment
        }
      },
      placeholder = { Text(text = stringResource(R.string.comment)) },
      singleLine = true,
    )
    Button(
      modifier = Modifier
          .padding(16.dp)
          .align(Alignment.CenterHorizontally),
      onClick = {
        val updatedPictureData = pictureData?.let {
          PictureData(
            id = selectedId,
            uri = it.uri,
            comment = comment,
          )
        }
        updatedPictureData?.let { selectedPictureData(selectedId, it) }
      }
    ) {
      Text(text = stringResource(R.string.post))
    }
  }
}
