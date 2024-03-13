package com.example.myalbum.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.feature.second.SecondScreen
import com.example.myalbum.feature.third.ThirdScreen
import com.example.myalbum.feature.top.TopScreen
import kotlinx.coroutines.launch

@Composable
fun MainNav(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Myアルバム", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "アルバム１") },
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
                    label = { Text(text = "アルバム２") },
                    selected = false,
                    onClick = {
                        navController.navigate(MainNavOption.SecondScreen.name) {
                            popUpTo(id = navController.graph.id)
                        }
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "アルバム３") },
                    selected = false,
                    onClick = {
                        navController.navigate(MainNavOption.ThirdScreen.name) {
                            popUpTo(id = navController.graph.id)
                        }
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        },
    )
    {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Menu") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            TopScreen(
                onNavigationToEditScreen = { /*TODO*/ },
                onNavigationToPreviewScreen = { /*TODO*/ },
                onUpPress = {},
            )
    NavHost(
        navController = navController,
        startDestination = MainNavOption.TopScreen.name,
    ) {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(
                onUpPress = {
                    coroutineScope.launch {
                        drawerState.apply { if (isClosed) open() else close() }
                    }
                },
                onNavigationToEditScreen = {},
                onNavigationToPreviewScreen = {},
            )
        }
        composable(MainNavOption.SecondScreen.name) {
            SecondScreen(onUpPress = { /*TODO*/ })
        }
        composable(MainNavOption.ThirdScreen.name) {
            ThirdScreen(onUpPress = { /*TODO*/ })
        }
    }
}
}

enum class MainNavOption {
    TopScreen,
    SecondScreen,
    ThirdScreen,
}