package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
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
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen

@Composable
fun MainNav(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: MainViewModel = hiltViewModel()
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
                        navController.navigate(MainNavOption.TopScreen.name)
                    }

                    MainNavOption.EditScreen -> {
                        navController.navigate(MainNavOption.EditScreen.name)
                    }

                    MainNavOption.PreviewScreen -> {
                        navController.navigate(MainNavOption.PreviewScreen.name)
                    }
                }
            }
        }
    ) {
        val isOnboarded = viewModel.isOnboarded.collectAsState()
        NavHost(
            navController,
            startDestination = MainNavOption.TopScreen.name,
        ) {
            composable(MainNavOption.TopScreen.name) {
                TopScreen(
                    drawerState = drawerState,
                    onNavigationToEditScreen = { /*TODO*/ }
                ) {
                }
            }
            composable(MainNavOption.EditScreen.name) {
                EditScreen(onNavigationToTopScreen = { /*TODO*/ }) {
                }
            }
            composable(MainNavOption.PreviewScreen.name) {
                PreviewScreen(onNavigationToTopScreen = { /*TODO*/ }) {
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
    navigation(startDestination = MainNavOption.TopScreen.name, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(
                drawerState = drawerState,
                onNavigationToPreviewScreen = {},
                onNavigationToEditScreen = {},
            )
        }
        composable(MainNavOption.EditScreen.name) {
            EditScreen(
                onNavigationToPreviewScreen = {},
                onNavigationToTopScreen = {},
            )
        }
        composable(MainNavOption.PreviewScreen.name) {
            PreviewScreen(
                onNavigationToEditScreen = {},
                onNavigationToTopScreen = {},
            )
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
