package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.core.data.DrawerParams
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen

@Composable
fun MainNavHost(
    onFinishApp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    startDestination: String = "TopScreen",
) {
    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    drawerState = drawerState,
                    menuItems = DrawerParams.drawerButtons,
                    defaultPick = MainNavOption.TopScreen,
                ) { onUsersPickedOption ->
                    when (onUsersPickedOption) {
                        MainNavOption.TopScreen -> {
                            navController.navigate(onUsersPickedOption.name) {
                                popUpTo(NavRoutes.MainRoute.name)
                            }
                        }

                        MainNavOption.EditScreen -> {
                            navController.navigate(onUsersPickedOption.name) {
                                popUpTo(NavRoutes.MainRoute.name)
                            }
                        }

                        MainNavOption.PreviewScreen -> {
                            navController.navigate(onUsersPickedOption.name) {
                                popUpTo(NavRoutes.MainRoute.name)
                            }
                        }
                    }
                }
            }
        ) {
            val isOnboarde = viewModel.isOnboarded.collectAsState()
            NavHost(
                navController = navController,
                startDestination = if (isOnboarde.value)
                    NavRoutes.MainRoute.name else
                    NavRoutes.IntroRoute.name {
                        introGraph(navController)
                        mainGraph(drawerState)
                    }
            ) {
                composable("TopScreen") {
                    TopScreen(
                        viewModel = hiltViewModel(),
                        navController = navController,
                        drawerState = DrawerState()
                    )
                }
                composable("EditScreen") {
                    EditScreen(
                        navController = navController,
                    )
                }
                composable("PreviewScreen") {
                    PreviewScreen(
                        navController = navController
                    )
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
    PreviewScreen
}