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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.R

@Composable
fun TopScreen(
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit,
    onUpPress: () -> Unit,
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
        onUpPress = onUpPress,
        onNavigationToEditScreen = onNavigationToEditScreen,
        onNavigationToPreviewScreen = onNavigationToPreviewScreen,
        navController = rememberNavController(),
    )
}

@Composable
fun TopScreenContent(
    onUpPress: () -> Unit,
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit,
    navController: NavController,
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
    Scaffold(
        botBar = { AddButton() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                println("Clicked!")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        }
    )

@Composable
fun AddButton() {
    FloatingActionButton(onClick = { /*do something*/ }) {
        Icon(Icons.Filled.Add, contentDescription = "追加")
    }
}
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreen(
        onUpPress = {},
        onNavigationToEditScreen = {},
        onNavigationToPreviewScreen = {},
    )
}
