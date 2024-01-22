package com.example.myalbum.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen

@Composable
fun MainNavHost(
    onFinishApp:() -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "top",
) {
   NavHost(
       modifier = modifier,
       navController = navController,
       startDestination = startDestination,

       ) {
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
