package com.example.myalbum.feature.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myalbum.R
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.core.data.PhotoRepository
import kotlinx.coroutines.launch

@Composable
fun <T : Enum<T>> TopScreen(
    drawerState: DrawerState,
    menuItems: String,
    defaultPick: T,
    onClick: (T) -> Unit,
    viewModel: TopViewModel,
) {
    var currentPick by remember { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = MaterialTheme.shapes.small,
                drawerContentColor = MaterialTheme.colorScheme.primaryContainer,
                drawerContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                drawerTonalElevation = 4.dp,
            ) {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = menuItems) },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("追加") },
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
        )
        { paddingValues ->
            Surface {
                Column(modifier = Modifier.padding(paddingValues)) {
                    val photos = listOf(
                        R.drawable.seigo1,
                        R.drawable.seigo2,
                        R.drawable.seigo3,
                        R.drawable.seigo4,
                        R.drawable.seigo5,
                        R.drawable.seigo9,
                    )
                    TopScreenContent(photos = photos)
                }
            }
        }
    }
}

@Composable
fun TopScreenContent(photos: List<Int>) {
    var selectedPhoto by remember { mutableStateOf(-1) }

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

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
        TopScreen(
        drawerState = DrawerState(initialValue = DrawerValue.Closed),
        menuItems = "編集する",
        defaultPick = MainNavOption.TopScreen,
        onClick = { },
        viewModel = TopViewModel(repository = PhotoRepository())
    )
}