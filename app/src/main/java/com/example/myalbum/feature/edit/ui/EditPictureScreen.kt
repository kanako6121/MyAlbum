package com.example.myalbum.feature.edit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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

  LaunchedEffect(key1 = albumId, key2 = pictureId) {
    viewModel.setEditPicture(albumId, pictureId)
  }
  EditPictureContent(
    modifier = Modifier.fillMaxSize(),
    onUpPress = onUpPress,
    onSaveComment = viewModel::savePicture,
    pictureData = uiState.pictureData,
  )
}

@Composable
fun EditPictureContent(
  modifier: Modifier = Modifier,
  onUpPress: () -> Unit,
  onSaveComment: (String) -> Unit,
  pictureData: PictureData?,
) {
  var comment by remember(pictureData) { mutableStateOf(pictureData?.comment.orEmpty()) }
  val zoomState = rememberZoomState()
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusRequester = remember { FocusRequester() }
  var (text, setText) = remember { mutableStateOf("Close keyboard on done ime action (blue ✔️)") }

  Scaffold(
    modifier = modifier,
    topBar = {
      EditTopBar(
        modifier = Modifier.fillMaxWidth().alpha(
          if (zoomState.scale <= 1f) 1f else 0f
        ),
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
        .imePadding()
    ) {
      if (LocalInspectionMode.current) {
        Image(
          painter = ColorPainter(color = Color.Gray),
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .zoomable(zoomState)
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp),
          contentDescription = null
        )
      } else {
        AsyncImage(
          onSuccess = { state ->
            zoomState.setContentSize(state.painter.intrinsicSize)
          },
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .zoomable(zoomState)
            .padding(8.dp)
            .fillMaxWidth(),
          model = pictureData?.uri,
          contentDescription = null
        )
      }

      if (zoomState.scale <= 1f) {
        OutlinedTextField(
          modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
          value = comment,
          onValueChange = { newComment ->
            comment = newComment
          },
          singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
          modifier = Modifier
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
    onUpPress = {},
    onSaveComment = { _ -> },
    pictureData = PictureData(id = 0, uri = "content://media/external/images/media/1".toUri(), comment = ""),
  )
}
