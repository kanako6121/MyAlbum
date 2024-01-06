package com.example.myalbum.feature.top

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myalbum.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TopScreen(
    onNavigateEditScreen: () -> Unit,
    onNavigatePreviewScreen: () -> Unit,
    onNavigateUp: () -> Unit,
    onExpandMenu: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopScreenAppBar(
                onNavigateUp = onNavigateUp,
                title = String(),
            )
        },
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
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
                onNavigateUp = onNavigateUp,
                onNavigateEditScreen = onNavigateEditScreen,
                onNavigatePreviewScreen = onNavigatePreviewScreen,
                photos = photos,
            )
        }
    }
}

@Composable
fun TopScreenContent(
    onNavigateUp: () -> Unit,
    onNavigateEditScreen: () -> Unit,
    onNavigatePreviewScreen: () -> Unit,
    photos: List<Int>
) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreenAppBar(
    onNavigateUp: () -> Unit,
    title: String,
    modifier: Modifier? = Modifier,
    navigationIcon: (@Composable () -> Unit)? = {},
    colors: TopAppBarColors? = TopAppBarDefaults.centerAlignedTopAppBarColors(),
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
            }
            Icon(
                imageVector = Icons.TwoTone.Menu, contentDescription = null
            )
        },
        title = { Text(text = stringResource(id = R.string.label_title)) },
    )
}


@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreen(
        onNavigateUp = {},
        onNavigateEditScreen = {},
        onNavigatePreviewScreen = {},
        onExpandMenu = {},
    )
}