package com.example.myalbum.main

import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

@Composable
fun MainNavHost(
    navController: NavController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: TopViewModel = hiltViewModel()
    ) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defalutPick = MainNavPotion.TopScerrn
            ) { onUserPickedOption ->
                when (onUserPickedOption) {
                    MainNavOption.TopScreen -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    ManNavOption.EditScerrn -> {
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
        NavHost(
            navController,
            startDestination = if (isOnboarded.value) NavRoutes.MainRoutes.name
            else NavRoutes.IntroRoute.name
        ) {
            introGraph(navController)
            mainGraph(drawerState)
        }
    }
}

fun NavGraphBuilder.mainGraph(drawerState: DrawerState) {
    navigation(startDestination = MainNavOption.TopScreen.name, route = NavRoute.name) {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(
                onNavigateEditScreen = { /*TODO*/ },
                onNavigatePreviewScreen = { /*TODO*/ },
                onNavigateUp = { /*TODO*/ }) {
                
            }
        }
    }
}