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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.R
import com.example.myalbum.feature.edit.ui.EditScreen
import com.example.myalbum.feature.top.ui.TopScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav(
  mainViewModel: MainViewModel,
  launchPicker: () -> Unit,
  navController: NavHostController = rememberNavController(),
  drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
  val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
  var showDialog by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
          items(
            items = uiState.albumMenus,
            key = { it.id }
          ) { item ->
            ModalDrawerAlbumItem(
              title = item.title,
              thumbnailUri = item.uri,
              onClick = {
                navController.navigate("album/${item.id}")
              }
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
          Text(text = stringResource(id = R.string.new_album))
        }
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text(text = stringResource(id = R.string.app_name)) },
          modifier = Modifier.fillMaxWidth(),
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
          }
        )
      },
    )
    { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        NavHost(
          navController = navController,
          startDestination = MainNavOption.TopScreen.name,
        ) {
          composable(MainNavOption.TopScreen.name) {
            TopScreen(
              viewModel = mainViewModel,
              launchPicker = launchPicker,
              onUpPress = {
                coroutineScope.launch {
                  if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
              },
              onEditScreen = { pictureData ->
                navController.navigate("edit/${pictureData.id}")
              },

              )
          }
          composable("edit/{selectId}") { backStackEntry ->
            EditScreen(
              selectedId = backStackEntry.arguments?.getString("selectId")?.toInt(),
              onClick = {
              },
            )
          }
        }
      }
    }
  }
//  if (showDialog) {
//    AlbumDialog(
//      mainViewModel = mainViewModel,
//      onDismiss = { showDialog = false },
//    )
//  }
}

@Composable
fun AlbumDialog(
  mainViewModel: MainViewModel,
  onDismiss: () -> Unit,
) {
  var albumTitle by remember { mutableStateOf("") }

  AlertDialog(
    title = {
      Text(text = stringResource(R.string.make_album))
    },
    text = {
      TextField(
        value = albumTitle,
        onValueChange = { albumTitle = it },
        label = { Text(text = stringResource(R.string.make_album)) }
      )
    },
    onDismissRequest = { onDismiss() },
    confirmButton = {
      TextButton(
        onClick = {
          if (albumTitle.isNotEmpty()) {
          }
          onDismiss()
        }
      )
      {
        Text(text = stringResource(R.string.save))
      }
    },
    dismissButton = {
      TextButton(
        onClick = onDismiss
      ) {
        Text(text = stringResource(R.string.cancel))
      }
    },
  )
}

enum class MainNavOption {
  TopScreen,
  EditScreen,
}
