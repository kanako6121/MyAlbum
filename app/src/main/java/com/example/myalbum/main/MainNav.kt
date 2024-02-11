package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myalbum.R
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen


@Composable
fun MainNav(
    navController: NavController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    vm: MainViewModel = hiltViewModel()
) {
    MyAlbumTheme {
        Surface {
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
                                navController.navigate(NavRoutes.MainRoute.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }
                            MainNavOption.EditScreen -> {
                                navController.navigate(NavRoutes.EditRoute.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }

                            MainNavOption.PreviewScreen -> {
                                navController.navigate(NavRoutes.PreviewRoute.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }
                        }
                    }
                }
            ) {
                val isOnboarded = vm.isOnboarded.collectAsState()
                NavHost(
                    navController,
                    startDestination = if (isOnboarded.value) NavRoutes.MainRoute.name else NavRoutes.EditRoute.name
                ) {
                    editGraph(navController)
                    mainGraph(drawerState)
                }
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
    navigation(startDestination = MainNavOption.TopScreen, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(drawerState)
        }
        composable(MainNavOption.EditScreen.name) {
            EditScreen(drawerState)
        }
        composable(MainNavOption.PreviewScreen.name) {
            PreviewScreen(drawerState)
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