package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.core.data.DrawerParams
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.core.theme.MyAlbumTheme

@Composable
fun MainCompose(
    navController: NavController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: ViewModel = hiltViewModel(),
) {
    MyAlbumTheme {
        Surface {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    AppDrawerContent(
                        drawerState = drawerState,
                        menuItems = DrawerParams.drawerButtons,
                        defaultPick = MainNavOption.TopScreen,
                        onClick = {},
                    ) { onUserPickedOption ->
                        when(onUserPickedOption) {
                            MainNavOption.TopScreen -> {
                                navController.navigate(onUserPickedOption.) {
                                    popUpTo()
                                }
                            }
                            MainNavOption.EditScreen -> {
                                navController.navigate(onUserPickedOption.edit) {
                                    popUpTo((NavRoutes.ManRoute.edit))
                                }
                            }

                        }

                    }
                }
            ) {

            }
        }
    }
}