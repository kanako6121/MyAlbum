package com.example.myalbum.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun MainNav(
  navController: NavHostController = rememberNavController(),
  drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
  vm: MainViewModel = hiltViewModel()
) {
  val coroutineScope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Text("圭菜メニュー", modifier = Modifier.padding(16.dp))

        Divider()

        NavigationDrawerItem(
          label = { Text(text = "圭菜トップ画面だよん") },
          selected = false,
          onClick = {
            navController.navigate(MainNavOption.TopScreen.name) {
              // バックスタックに遷移履歴を積まないように遷移前の履歴を全部消す
              popUpTo(id = navController.graph.id)
            }
            coroutineScope.launch { drawerState.close() }
          }
        )

        Divider()

        NavigationDrawerItem(
          label = { Text(text = "圭菜へんしゅう開くんよ") },
          selected = false,
          onClick = {
            navController.navigate(MainNavOption.EditScreen.name) {
              popUpTo(id = navController.graph.id)
            }
            coroutineScope.launch { drawerState.close() }
          }
        )

        Divider()

        NavigationDrawerItem(
          label = { Text(text = "圭菜プレビュー出します") },
          selected = false,
          onClick = {
            navController.navigate(MainNavOption.PreviewScreen.name) {
              popUpTo(id = navController.graph.id)
            }
            coroutineScope.launch { drawerState.close() }
          }
        )
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

enum class MainNavOption {
  TopScreen,
  EditScreen,
  PreviewScreen,
}
