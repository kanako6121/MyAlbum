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
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.NavController
import com.example.myalbum.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreen(
    viewModel: TopViewModel,
    navController: NavController,
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = {
            val coroutineScope = rememberCoroutineScope()
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = "MenuButton"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    TopScreen(
        viewModel = TopViewModel,
        navController = NavController(),
        drawerState = drawerState,
    )
}
