package com.example.myalbum.feature.main.ui

import androidx.compose.runtime.Composable
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
