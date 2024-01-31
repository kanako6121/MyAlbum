package com.example.myalbum.feature.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myalbum.R
import com.example.myalbum.core.data.DrawerMenuItem
import com.example.myalbum.core.data.MainNavOption

@Composable
fun <T : Enum<T>> TopScreen(
    menuItems: String,
    viewModel: TopViewModel,
    defaultPick: T,
    drawerState: DrawerState,
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit
) {
    val photos = listOf(
        R.drawable.seigo1,
        R.drawable.seigo2,
        R.drawable.seigo3,
        R.drawable.seigo4,
        R.drawable.seigo5,
        R.drawable.seigo9,
    )
    TopScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreenContent() {
    var selectedPhoto by remember { mutableStateOf(-1) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val photos = listOf(
        R.drawable.seigo1,
        R.drawable.seigo2,
        R.drawable.seigo3,
        R.drawable.seigo4,
        R.drawable.seigo5,
        R.drawable.seigo9,
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { "Menu" },
                navigationIcon = {
                    IconButton(
                        onClick = {drawerState.isOpen},
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {},
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                content = {
                    items(photos.size) { index ->
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                                .shadow(elevation = 4.dp)
                                //.aspectRatio(1.5f, matchHeightConstraintsFirst = false)
                                .background(Color.White)
                                .border(
                                    BorderStroke(width = 0.5.dp, color = Color.Gray)
                                )
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 24.dp),
                        ) {
                            Image(
                                painter = painterResource(id = photos.get(index)),
                                contentDescription = "Photo",
                                contentScale = ContentScale.Fit
                            )
                            Text(text = "運動会")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TopAppBar() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                ModalDrawerSheet(
                    drawerShape = MaterialTheme.shapes.small,
                    drawerContentColor = MaterialTheme.colorScheme.primaryContainer,
                    drawerContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    drawerTonalElevation = 4.dp,
                ) {
                    Text("Menu", modifier = Modifier.padding(16.dp))
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                    DrawerMenuItem(icon = Icons.Default.Edit, "編集する")
                    DrawerMenuItem(icon = Icons.Default.Check, label = "プレビュー")
                }
            }
        },
        content = {
            TopScreenContent()
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreen(
        menuItems = "編集する",
        viewModel = viewModel(),
        defaultPick = MainNavOption.TopScreen,
        drawerState = DrawerState(initialValue = DrawerValue.Closed),
        onNavigationToEditScreen = {},
        onNavigationToPreviewScreen = {},
    )
}