package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.core.data.DrawerParams
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

@Composable
fun MainNavHost(
) {

}

@Composable
fun NavHost(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: TopViewModel = hiltViewModel()
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
                        when (onUserPickedOption ) {
                            MainNavOption.TopScreen -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }
                            MainNavOption.EditScreen -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }
                            MainNavOption.PreviewScreen -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(NavRoutes.MainRoute.name)
                                }
                            }
                        }
                    }
                }
            ) {
                val isOnboarded = viewModel.isOnboarded.collectAsState()
                NavHost(
                    navController,
                    startDestination = if (isOnboarded.value) NavRoutes.MainRoute.name else NavRoutes.IntroRoute.name
                ) {
                    introGraph(navController)
                    mainGraph(drawerState)
                }
            }
        }
    }
}
fun NavGraphBuilder.mainGraph(drawerState: DrawerState) {
    navigation(startDestination = MainNavOption.TopScreen.name, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.TopScreen.name){
            TopScreen(drawerState)
        }
        composable(MainNavOption.EditScreen.name){
            EditScreen(drawerState)
        }
        composable(MainNavOption.PreviewScreen.name){
            PreviewScreen(drawerState)
        }
    }
}
enum class MainNavOption {
    TopScreen,
    EditScreen,
    PreviewScreen,
}