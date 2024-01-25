package com.example.myalbum.main

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel
import kotlinx.coroutines.launch

@Composable
fun MainNavGraph(
    appContainer: AppContainer,
    isExPandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = NavigationDestinations.TOP_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    )
    {
        composable(
            route = NavigationDestinations.TOP_ROUTE,
            deepLinks = {
                uriPattern =
                    "                        \"$JETNEWS_APP_URI/${JetnewsDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}\"\n"
            }
        ) { navBackStackEntry ->
            val topViewModel: TopViewModel = viewModel(
                factory = TopViewModel.provideFactory(
                    postRepository = appContainer.postRepository,
                    preSelectedPostId = navBackStackEntry.arguments?.getString(POST_ID)
                )
            )
            TopScreen(
                viewModel = topViewModel,
                navController = navController,
                isExPandedScreen = isExPandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(NavigationDestinations.EDIT_ROUTE) {
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

val navigationAction = remember(navController) { NavigationAction(navController) }
val coroutineScope = rememberCoroutineScope()
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentRoute = navBackStackEntry?.destination?.route ?: NavigationDestinations.TOP_ROUTE
val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

ModalNavigationDrawer(
drawerContent = {
    AppDrawer(
        currentRoute = currentRoute,
        navigateToTop = navigationAction.navigateToTop,
        navigateToEdit = navigationAction.navigateToEdit,
        navigateToPreview = navigationAction.navigateToPreview,
        closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
    )
},
drawerState = sizeAwareDrawerState,
gesturesEnabled = !isExpandedScreen
) {
    Row {
        if (isExpandedScreen) {
            AppNavRail(
                currentRoute = currentRoute,
                navigateToHome = navigationAction.navigateToTop,
                navigateToEdit = navigationAction.navigateToEdit,
                navigateToPreview = navigationAction.navigateToPreview,
            )
        }
        MainNavGraph(
            appContainer = appContainer,
            isExpandedScreen = isExpandedScreen,
            openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
        )
    }
}

fun NavGraphBuilder.mainGraph(drawerState: DrawerState) {
    navigation(startDestination = MainNavOption.TopScreen.name, route = "TopScreen") {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(drawerState)
        }
        composable(MainNavOption.EditScreen.name) {
            EditScreen(drawerState)
        }
        composable(MainNavOption.PreviewScreen.name) {
            PreviewScreen(drawerState)
        }
    }
}
