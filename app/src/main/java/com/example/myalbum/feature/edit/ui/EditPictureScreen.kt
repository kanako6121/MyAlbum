package com.example.myalbum.feature.edit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
    maxChar = maxChar,
    onUpPress = onUpPress,
    onSaveComment = viewModel::savePicture,
    pictureData = uiState.pictureData,
    currentAlbum = viewModel.uiState.value.
  )
}

@Composable
fun EditPictureContent(
  maxChar: Int,
  onUpPress: () -> Unit,
  onSaveComment: (String) -> Unit,
  pictureData: PictureData?,
  drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
  var comment by remember(pictureData) { mutableStateOf(pictureData?.comment.orEmpty()) }
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      EditTopBar(
        title = {Text(text = uiState.)},
        navigationIcon = { drawerState.isOpen },
        Icon(imageVector = Icons.Default.Menu, contentDescription = null),
      )
    }
  ) { paddings ->
    Column(
      modifier = Modifier.padding(paddings)
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

@Composable
fun EditTopBar(
  title: () -> Unit,
  navigationIcon: () -> Unit,
  icon: Unit,
) {
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  ModalNavigationDrawer(
    drawerState = drawerState,
    gesturesEnabled = drawerState.isOpen,
    drawerContent = {
      Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
      ) {
        ModalDrawerSheet(
          drawerShape = MaterialTheme.shapes.small,
          drawerContentColor = MaterialTheme.colorScheme.primaryContainer,
          drawerContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
          drawerTonalElevation = 4.dp,
        ) {
          Text("menu", modifier = Modifier.padding(16.dp))
          Divider(color = Color.Gray, thickness = 0.5.dp)
          DrawerMenuItem(icon = Icons.Default.Edit, label = "menu")
          DrawerMenuItem(icon = Icons.Default.Check, label = "プレビュー")
        }
      }
    },
    content = {
      EditPictureScreen { }(
        drawerState = drawerState,
        onNavigationToEditScreen = { },
      ) {

      }
    },
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
