package com.example.myalbum.feature.top

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.myalbum.core.data.PictureData

@Composable
fun TopScreen(
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit,
    onUpPress: () -> Unit,
    viewModel: TopViewModel,
) {
    val pictureData by viewModel.
    TopScreenContent(
        onUpPress = onUpPress,
        onNavigationToEditScreen = onNavigationToEditScreen,
        onNavigationToPreviewScreen = onNavigationToPreviewScreen,
        onClick = {},
    )
}

@Composable
fun TopScreenContent(
    onUpPress: () -> Unit,
    onNavigationToEditScreen: () -> Unit,
    onNavigationToPreviewScreen: () -> Unit,
    onClick: (PictureData) -> Unit,
) {
    var pickedImageUri by remember { mutableStateOf(Uri.EMPTY) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        uri: Uri? ->
        uri?.let {
            pickedImageUri = it
        }
    }
    LaunchedEffect(true) {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick =
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    )
    { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                content = {
                 items(pickedImageUri)  { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                                .shadow(elevation = 4.dp)
                                //.aspectRatio(1.5f, matchHeightConstraintsFirst = false)
                                .background(Color.White)
                                .border(
                                    BorderStroke(width = 0.5.dp, color = Color.Gray)
                                )
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 24.dp),
                        )
                        Text(text = "写真")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreenContent(
        onUpPress = {},
        onNavigationToPreviewScreen = {},
        onNavigationToEditScreen = {},
        onClick = {}
    )
}