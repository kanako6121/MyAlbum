package com.example.myalbum.feature.album.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.feature.main.ui.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun AlbumScreen(
  viewModel: MainViewModel,
  launchPicker: () -> Unit,
  onEditScreen: (PictureData) -> Unit,
  onUpPress: () -> Unit,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  AlbumScreenContent(
    launchPicker = launchPicker,
    onUpPress = onUpPress,
    onEditScreen = onEditScreen,
    currentAlbumData = uiState.currentAlbum,
    onRemove = viewModel::removePhoto,
  )
}

@Composable
fun AlbumScreenContent(
  launchPicker: () -> Unit,
  onUpPress: () -> Unit,
  onEditScreen: (PictureData) -> Unit,
  currentAlbumData: AlbumData,
  onRemove: (Int, Int) -> Unit,
) {
  val scrollState = rememberLazyStaggeredGridState()
  var showButton by remember { mutableStateOf(true) }
  var showTutorial by remember { mutableStateOf(true) }

  LaunchedEffect(currentAlbumData) {
    delay(600)
    showTutorial = currentAlbumData.pictures.isEmpty()
    snapshotFlow { scrollState.isScrollInProgress }
      .collect { scrolling ->
        showButton = !scrolling
      }
  }
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding(),
    floatingActionButton = {
      AnimatedVisibility(
        visible = showButton,
        enter = scaleIn(animationSpec = tween(delayMillis = 1000)) + fadeIn(
          animationSpec = tween(
            delayMillis = 1000
          )
        ),
        exit = scaleOut() + fadeOut(),
      )
      {
        FloatingActionButton(
          onClick = launchPicker,
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "add")
        }
      }
    },
    floatingActionButtonPosition = FabPosition.End,
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
    ) {
      LazyVerticalStaggeredGrid(
        state = scrollState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
      ) {
        items(currentAlbumData.pictures) { pictureData ->
          var expanded by remember { mutableStateOf(false) }
          Box(
            modifier = Modifier.padding(
              start = 8.dp,
              top = 8.dp,
              end = 4.dp,
              bottom = 8.dp
            )
          ) {
            AsyncImage(
              model = currentAlbumData.pictures,
              contentDescription = null,
              modifier = Modifier
                .shadow(elevation = 4.dp)
                .background(Color.White)
                .border(
                  BorderStroke(width = 0.5.dp, color = Color.Gray)
                )
                .padding(
                  start = 8.dp,
                  top = 8.dp,
                  end = 8.dp,
                  bottom = 32.dp
                )
                .clickable { onEditScreen(pictureData) },
            )
            Box(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 4.dp)
            ) {
              Text(
                text = pictureData.comment.orEmpty()
              )
            }
            Box(
              modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomStart)
                .padding(start = 0.dp, bottom = 4.dp)
            ) {
              IconButton(onClick = { expanded = true }) {
                Icon(
                  imageVector = Icons.Default.MoreVert,
                  contentDescription = "",
                  tint = MaterialTheme.colorScheme.onSurface
                )
              }
              DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
              ) {
                DropdownMenuItem(
                  text = { Text(stringResource(id = R.string.description_delete)) },
                  onClick = {
                    onRemove(currentAlbumData.id, pictureData.id)
                    expanded = false
                  },
                  leadingIcon = {
                    Icon(
                      imageVector = Icons.Default.Delete,
                      contentDescription = null
                    )
                  }
                )
              }
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
  AlbumScreenContent(
    launchPicker = {},
    onUpPress = {},
    onEditScreen = {},
    currentAlbumData = AlbumData(id = 0, title = "プレビュー", pictures = emptyList()),
    onRemove = { _, _ -> }
  )
}
