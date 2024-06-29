package com.example.myalbum.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.R
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.second.SecondScreen
import com.example.myalbum.feature.third.ThirdScreen
import com.example.myalbum.feature.top.TopScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav(
    mainViewModel: MainViewModel,
    launchPicker: () -> Unit,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestinagtion: MainNavOption = MainNavOption.TopScreen,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val coroutineScope = rememberCoroutineScope()
        TopAppBar(
            title = { Text(text = "Menu") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
            ),
            navigationIcon = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = null
                    )
                }
            }
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Myアルバム", modifier = Modifier.padding(16.dp))
                    Divider()
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.album1)) },
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
                        label = { Text(text = stringResource(R.string.album2)) },
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
                        label = { Text(text = stringResource(R.string.album3)) },
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
        ) {
            Scaffold(
                modifier = Modifier.fillMaxWidth(),
            )
            { contentPadding ->
                Column(
                    modifier = Modifier.padding(contentPadding),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = MainNavOption.TopScreen.name,
                    ) {
                        composable(MainNavOption.TopScreen.name) {
                            TopScreen(
                                viewModel = mainViewModel,
                                launchPicker = launchPicker,
                                onUpPress = {
                                    coroutineScope.launch {
                                        drawerState.apply { if (isClosed) open() else close() }
                                    }
                                },
                                onEditScreen = { pictureData ->
                                    navController.navigate("edit/${pictureData.id}")
                                },
                            )
                        }
                        composable(MainNavOption.SecondScreen.name) {
                            SecondScreen(onUpPress = { /*TODO*/ })
                        }
                        composable(MainNavOption.ThirdScreen.name) {
                            ThirdScreen(onUpPress = { /*TODO*/ })
                        }
                        composable("edit/{selectId}") { backStackEntry ->
                            EditScreen(
                                selectedId = backStackEntry.arguments?.getString("selectId")
                                    ?.toInt(),
                                onClick = { pictureData ->
                                    mainViewModel.saveEditPhoto(pictureData)
                                    navController.popBackStack()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class MainNavOption {
    TopScreen,
    SecondScreen,
    ThirdScreen,
}
