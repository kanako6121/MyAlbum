package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.R
import com.example.myalbum.feature.edit.ui.EditPictureScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav(
  mainViewModel: MainViewModel,
  launchPicker: () -> Unit,
  navController: NavHostController = rememberNavController(),
  drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
  startDestination: String = "album",
) {
  val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
  var showDialog by remember { mutableStateOf(false) }
  var editShowDialog by remember { mutableStateOf(false) }
  var showDeleteDialog by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
        ) {
          items(
            items = uiState.albumMenus,
            key = { it.id }
          ) { item ->
            ModalDrawerAlbumItem(
              title = item.title,
              thumbnailUri = item.uri,
              onClick = {
                mainViewModel.selectAlbum(item.id)
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
            .clickable { showDialog = true }
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
          )
          Text(text = stringResource(id = R.string.make_album))
        }
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Text(text = uiState.currentAlbum.title)
          },
          modifier = Modifier
            .fillMaxWidth()
            .clickable { editShowDialog = true },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
          ),
          navigationIcon = {
            IconButton(
              onClick = {
                coroutineScope.launch {
                  if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
              }
            ) {
              Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
          },
          actions = {
            IconButton(onClick = { editShowDialog = true }) {
              Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = { showDeleteDialog = true }) {
              Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
          },
        )
      }
    )
    { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        NavHost(
          navController = navController,
          startDestination = startDestination,
        ) {
          composable("album") {
            AlbumScreen(
              viewModel = mainViewModel,
              launchPicker = launchPicker,
              onUpPress = {
                coroutineScope.launch {
                  if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
              },
              navigateEditScreen = { albumId, pictureData ->
                navController.navigate("edit/${albumId}/${pictureData.id}")
              },
            )
          }
          composable("edit/{albumId}/{selectId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")?.toIntOrNull() ?: return@composable
            val pictureId = backStackEntry.arguments?.getString("selectId")?.toIntOrNull() ?: return@composable
            EditPictureScreen(
              albumId = albumId,
              pictureId = pictureId,
              viewModel = hiltViewModel(),
              onUpPress = { navController.popBackStack() },
            )
          }
        }
      }
    }
  }
  if (showDialog) {
    CreateTitleDialog(
      onDismiss = { showDialog = false },
      onAddTitle = mainViewModel::createAlbumTitle,
    )
  }
  if (editShowDialog) {
    EditTitleDialog(
      onDismiss = { editShowDialog = false },
      updateTitle = mainViewModel::updateAlbumTitle,
      currentAlbum = uiState.currentAlbum,
    )
  }
  if (showDeleteDialog) {
    DeleteAlbumDialog(
      onDismiss = { showDeleteDialog = false },
      onDeleteAlbum = mainViewModel::deleteAlbum,
      currentAlbum = uiState.currentAlbum,
    )
  }
}
