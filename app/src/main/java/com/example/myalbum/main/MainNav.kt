package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myalbum.R

@Composable
fun MainNav(
  navController: NavHostController = rememberNavController(),
  drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
  vm: MainViewModel = hiltViewModel()
) {
  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      AppDrawerContent(
        drawerState = drawerState,
        menuItems = DrawerParams.drawerButtons,
        defaultPick = MainNavOption.TopScreen
      ) { onUserPickedOption ->
        when (onUserPickedOption) {
          MainNavOption.TopScreen -> {
            // navController.navigate(NavRoutes.MainRoute.name) {
            //   popUpTo(NavRoutes.MainRoute.name)
            // }
            // TODO ↑ Nested Navigationをしたいみたいだけど、現状のコードが謎なので^^; TopScreen出します。
            navController.navigate(MainNavOption.TopScreen.name)
          }

          MainNavOption.EditScreen -> {
            // navController.navigate(NavRoutes.EditRoute.name) {
            //   popUpTo(NavRoutes.MainRoute.name)
            // }
            navController.navigate(MainNavOption.EditScreen.name)
          }

          MainNavOption.PreviewScreen -> {
            // navController.navigate(NavRoutes.PreviewRoute.name) {
            //   popUpTo(NavRoutes.MainRoute.name)
            // }
            navController.navigate(MainNavOption.PreviewScreen.name)
          }
        }
      }
    }
  ) {
    val isOnboarded = vm.isOnboarded.collectAsState()
    NavHost(
      navController,
      // startDestination = if (isOnboarded.value) NavRoutes.MainRoute.name else NavRoutes.EditRoute.name,
      // TODO 動作確認のため、とりあえずStart決め打ち（あとで置き換え）
      startDestination = MainNavOption.TopScreen.name,
    ) {
      composable(MainNavOption.TopScreen.name) {
        // TopScreen(drawerState)
        // TODO 切替確認するためダミーのTextを表示(あとでScreenに置き換える)
        Text(text = "TopScreen")
      }
      composable(MainNavOption.EditScreen.name) {
        // EditScreen(drawerState)
        // TODO 切替確認するためダミーのTextを表示(あとでScreenに置き換える)
        Text(text = "EditScreen")
      }
      composable(MainNavOption.PreviewScreen.name) {
        // PreviewScreen(drawerState)
        // TODO 切替確認するためダミーのTextを表示(あとでScreenに置き換える)
        Text(text = "PreviewScreen")
      }
    }
  }
}

enum class NavRoutes {
  MainRoute,
  EditRoute,
  PreviewRoute,
}

@Composable
fun NavGraphBuilder.mainGraph(drawerState: DrawerState) {
  navigation(startDestination = MainNavOption.TopScreen.name, route = NavRoutes.MainRoute.name) {
    composable(MainNavOption.TopScreen.name) {
      // TopScreen(drawerState)
      Text(text = "TopScreen")
    }
    composable(MainNavOption.EditScreen.name) {
      // EditScreen(drawerState)
      Text(text = "EditScreen")
    }
    composable(MainNavOption.PreviewScreen.name) {
      // PreviewScreen(drawerState)
      Text(text = "PreviewScreen")
    }
  }
}

enum class MainNavOption {
  TopScreen,
  EditScreen,
  PreviewScreen,
}

object DrawerParams {
  val drawerButtons = arrayListOf(
    AppDrawerItemInfo(
      MainNavOption.TopScreen,
      R.string.drawer_top,
      R.drawable.ic_launcher_foreground,
      R.string.drawer_top,
    ),
    AppDrawerItemInfo(
      MainNavOption.EditScreen,
      R.string.drawer_edit,
      R.drawable.ic_launcher_foreground,
      R.string.description_edit
    ),
    AppDrawerItemInfo(
      MainNavOption.PreviewScreen,
      R.string.drawer_preview,
      R.drawable.ic_launcher_foreground,
      R.string.description_preview
    )
  )
}
