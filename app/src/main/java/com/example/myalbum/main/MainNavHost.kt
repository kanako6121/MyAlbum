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
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

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
