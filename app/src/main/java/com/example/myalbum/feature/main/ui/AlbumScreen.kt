package com.example.myalbum.feature.main.ui

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.feature.main.ui.item.DraggablePictureItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlbumScreen(
  viewModel: MainViewModel = hiltViewModel(),
  launchPicker: () -> Unit,
  navigateEditScreen: (Int, PictureData) -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()
  val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var showCreateAlbumDialog by remember { mutableStateOf(false) }
  var showEditAlbumDialog by remember { mutableStateOf(false) }
  var showDeleteAlbumDialog by remember { mutableStateOf(false) }
  val currentAlbum = uiState.currentAlbum

  if (currentAlbum == null) return

  ModalNavigationDrawer(modifier = Modifier.fillMaxSize(), drawerState = drawerState, drawerContent = {
    ModalDrawerSheet {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
      ) {
        items(items = uiState.albumMenus, key = { it.id })
        { item ->
          ModalDrawerAlbumItem(
            title = item.title, thumbnailUri = item.uri,
            onClick = {
              viewModel.selectAlbum(item.id)
              coroutineScope.launch {
                if (drawerState.isClosed) drawerState.open() else drawerState.close()
              }
            },
          )
        }
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .padding(16.dp)
          .clickable { showCreateAlbumDialog = true },
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
        )
        Text(text = stringResource(id = R.string.make_album))
      }
    }
  }) {
    AlbumContent(
      modifier = Modifier.fillMaxSize(),
      currentAlbumData = currentAlbum,
      onEditTitle = { showEditAlbumDialog = true },
      onDeleteAlbum = { showDeleteAlbumDialog = true },
      launchPicker = launchPicker,
      onNavigateEditScreen = navigateEditScreen,
      onRemovePicture = viewModel::onRemovePhoto,
      onClickDrawMenu = {
        coroutineScope.launch {
          if (drawerState.isClosed) drawerState.open() else drawerState.close()
        }
      },
      onMovePicture = { moveId, targetId ->
        viewModel.onMovePicture(currentAlbum.id, moveId, targetId)
      },
    )
  }
  if (showCreateAlbumDialog) {
    CreateTitleDialog(
      onDismiss = { showCreateAlbumDialog = false },
      onAddTitle = viewModel::createAlbumTitle,
    )
  }
  if (showEditAlbumDialog) {
    EditTitleDialog(
      onDismiss = { showEditAlbumDialog = false },
      updateTitle = viewModel::updateAlbumTitle,
      currentAlbum = currentAlbum
    )
  }
  if (showDeleteAlbumDialog) {
    DeleteAlbumDialog(
      onDismiss = { showDeleteAlbumDialog = false },
      onDeleteAlbum = viewModel::deleteAlbum,
      currentAlbum = currentAlbum,
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumContent(
  modifier: Modifier = Modifier,
  currentAlbumData: AlbumData,
  onClickDrawMenu: () -> Unit,
  onEditTitle: () -> Unit,
  onDeleteAlbum: () -> Unit,
  launchPicker: () -> Unit,
  onNavigateEditScreen: (Int, PictureData) -> Unit,
  onRemovePicture: (Int, Int) -> Unit,
  onMovePicture: (Int, Int) -> Unit,
) {
  var showTutorial by remember { mutableStateOf(false) }
  val scrollState = rememberLazyStaggeredGridState()

  LaunchedEffect(currentAlbumData.pictures) {
    delay(600)
    showTutorial = currentAlbumData.pictures.isEmpty()
  }

  Scaffold(modifier = modifier, topBar = {
    AlbumTopBar(
      modifier = Modifier.clickable(onClick = onEditTitle),
      title = { Text(text = currentAlbumData.title) },
      navigationIcon = {
        IconButton(onClick = onClickDrawMenu) {
          Icon(imageVector = Icons.Default.Menu, contentDescription = null)
        }
      },
      actions = {
        IconButton(onClick = onEditTitle) {
          Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        }
        IconButton(onClick = onDeleteAlbum) {
          Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
      })
  }) { contentPadding ->
    Box(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize()
    ) {
      LazyVerticalStaggeredGrid(
        modifier = modifier,
        state = scrollState,
        columns = StaggeredGridCells.Fixed(2)
      ) {
        items(currentAlbumData.pictures) { pictureData ->
          DraggablePictureItem(
            pictureData = pictureData,
            onNavigateEditScreen = { picData ->
              onNavigateEditScreen(currentAlbumData.id, picData)
            },
            onRemovePicture = { picId ->
              onRemovePicture(currentAlbumData.id, picId)
            },
            onMovePicture = onMovePicture
          )
        }
      }
      AnimatedVisibility(
        modifier = Modifier.align(Alignment.BottomEnd),
        visible = showTutorial,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
      ) {
        Column(
          modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.End
        ) {
          Text(text = stringResource(R.string.tutorial))
          Spacer(modifier = Modifier.size(64.dp))
        }
      }
      FloatingActionButton(
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.BottomEnd), onClick = {
          showTutorial = false
          launchPicker()
        }) {
        Icon(
          imageVector = Icons.Default.Add, contentDescription = null
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopBar(
  modifier: Modifier,
  title: @Composable () -> Unit,
  navigationIcon: @Composable () -> Unit = {},
  actions: @Composable RowScope.() -> Unit = {},
) {
  TopAppBar(
    modifier = modifier,
    title = title,
    navigationIcon = navigationIcon,
    actions = actions,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      titleContentColor = MaterialTheme.colorScheme.primary,
    )
  )
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
  AlbumContent(
    launchPicker = {},
    onNavigateEditScreen = { _, _ -> },
    onRemovePicture = { _, _ -> },
    onClickDrawMenu = {},
    onEditTitle = {},
    onDeleteAlbum = {},
    currentAlbumData = AlbumData(id = 0, title = "プレビュー", pictures = emptyList()),
    onMovePicture = { _, _ -> }
  )
}
