package com.example.myalbum.feature.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myalbum.R

@Composable
fun TopScreen(
    viewModel: TopViewModel,
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
    TopScreenContent(
        onNavigationToEditScreen = onNavigationToEditScreen,
        onNavigationToPreviewScreen = onNavigationToPreviewScreen,
    )
}

@Composable
fun TopScreenContent(
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit,
) {
    var selectedPhoto by remember { mutableStateOf(-1) }
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
        topBar = {},
            )
    { paddings ->
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

/*@Composable
fun TopBar(title: () -> Unit, navigationIcon: () -> Unit) {
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
                    Text("menu", modifier = Modifier.padding(16.dp))
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                    DrawerMenuItem(icon = Icons.Default.Edit, label = "menu")
                    DrawerMenuItem(icon = Icons.Default.Check, label = "プレビュー")
                }
            }
        },
        content = {
            TopScreenContent(
                drawerState = drawerState,
                onNavigationToEditScreen = { },
            ) {

            }
        },
    )
}*/
@Composable
fun DrawerMenuItem(icon: ImageVector, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreen(
        viewModel = viewModel(),
        onNavigationToEditScreen = {},
        onNavigationToPreviewScreen = {},
    )
}