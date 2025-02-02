package com.example.myalbum.feature.main.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.feature.edit.ui.EditPictureScreen

@Composable
fun MainNav(
  mainViewModel: MainViewModel,
  launchPicker: () -> Unit,
  navController: NavHostController = rememberNavController(),
  startDestination: String = "album",
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {
    composable("album") {
      AlbumScreen(
        viewModel = mainViewModel,
        launchPicker = launchPicker,
        navigateEditScreen = { albumId, pictureData ->
          navController.navigate("edit/${albumId}/${pictureData.id}")
        }
      )
    }
    composable("edit/{albumId}/{selectId}") { backStackEntry ->
      val albumId = backStackEntry.arguments?.getString("albumId")?.toIntOrNull() ?: return@composable
      val pictureId = backStackEntry.arguments?.getString("selectId")?.toIntOrNull() ?: return@composable
      EditPictureScreen(
        albumId = albumId,
        pictureId = pictureId,
        onUpPress = { navController.popBackStack() },
      )
    }
  }
}
