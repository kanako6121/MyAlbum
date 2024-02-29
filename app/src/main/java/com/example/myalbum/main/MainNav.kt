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
import com.example.myalbum.feature.top.TopViewModel
import kotlinx.coroutines.launch

@Composable
fun MainNav(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Top") },
                    selected = false,
                    onClick = {
                        navController.navigate(MainNavOption.TopScreen.name) {
                            popUpTo(id = navController.graph.id)
                        }
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Edit") },
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
                    label = { Text(text = "Preview") },
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
    )
    {
        val isOnboarded = viewModel.isOnboarded.collectAsState()
        NavHost(
            navController,
            startDestination = MainNavOption.TopScreen.name,
        ) {
            composable(MainNavOption.TopScreen.name) {
                TopScreen(
                    viewModel = TopViewModel(),
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
