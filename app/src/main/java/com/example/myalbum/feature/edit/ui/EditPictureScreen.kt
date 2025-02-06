package com.example.myalbum.feature.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myalbum.R
import com.example.myalbum.core.data.PictureData
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun EditPictureScreen(
  albumId: Int,
  pictureId: Int,
  viewModel: EditPictureViewModel = hiltViewModel(),
  onUpPress: () -> Unit,
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val maxChar = 12

  LaunchedEffect(key1 = albumId, key2 = pictureId) {
    viewModel.setEditPicture(albumId, pictureId)
  }
  EditPictureContent(
    modifier = Modifier.fillMaxSize(),
    maxChar = maxChar,
    onUpPress = onUpPress,
    onSaveComment = viewModel::savePicture,
    pictureData = uiState.pictureData,
  )
}

@Composable
fun EditPictureContent(
  modifier: Modifier = Modifier,
  maxChar: Int,
  onUpPress: () -> Unit,
  onSaveComment: (String) -> Unit,
  pictureData: PictureData?,
) {
  var comment by remember(pictureData) { mutableStateOf(pictureData?.comment.orEmpty()) }

  Scaffold(
    modifier = Modifier,
    topBar = {
      EditTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(R.string.edit_photo)) },
        navigationIcon = {
          IconButton(onClick = onUpPress) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
          }
        },
      )
    }
  ) { contentPadding ->
    Column(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxWidth()
    ) {
      val zoomState = rememberZoomState()
      AsyncImage(
        onSuccess = { state ->
          zoomState.setContentSize(state.painter.intrinsicSize)
        },
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .zoomable(zoomState)
          .aspectRatio(1f)
          .padding(8.dp)
          .fillMaxWidth()
          .fillMaxHeight(),
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
          onSaveComment(comment)
          onUpPress()
        }
      ) {
        Text(text = stringResource(R.string.post))
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(
  modifier: Modifier = Modifier,
  title: @Composable () -> Unit,
  navigationIcon: @Composable () -> Unit = {},
) {
  TopAppBar(
    modifier = modifier,
    title = title,
    navigationIcon = navigationIcon,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      titleContentColor = MaterialTheme.colorScheme.primary,
    )
  )
}

@Preview(showBackground = true)
@Composable
fun ShowEditPictureContent() {
  EditPictureContent(
    maxChar = 10,
    onUpPress = {},
    onSaveComment = { _ -> },
    pictureData = PictureData(id = 0, uri = "content://media/external/images/media/1".toUri(), comment = ""),
  )
}
