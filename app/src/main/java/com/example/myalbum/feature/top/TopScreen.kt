package com.example.myalbum.feature.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.main.MainViewModel

@Composable
fun TopScreen(
    viewModel: MainViewModel,
    launchPicker: () -> Unit,
    onEditScreen: (PictureData) -> Unit,
    onUpPress: () -> Unit,
) {
    val pictures by viewModel.pictures.collectAsState()
    TopScreenContent(
        launchPicker = launchPicker,
        onUpPress = onUpPress,
        onEditScreen = onEditScreen,
        pictures = pictures,
        onSaveData = viewModel::savePhoto,
        onRemove = viewModel::removePhoto,
    )
}

@Composable
fun TopScreenContent(
    launchPicker: () -> Unit,
    onUpPress: () -> Unit,
    onEditScreen: (PictureData) -> Unit,
    pictures: List<PictureData>,
    onSaveData: (PictureData) -> Unit,
    onRemove: (PictureData) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = launchPicker
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
                columns = StaggeredGridCells.Fixed(2)
            ) {
                items(pictures) { pictureData ->
                    var expanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.padding(4.dp)) {
                        AsyncImage(
                            model = pictureData.uri,
                            contentDescription = null,
                            modifier = Modifier
                                .shadow(elevation = 4.dp)
                                .background(Color.White)
                                .border(
                                    BorderStroke(width = 0.5.dp, color = Color.Gray)
                                )
                                .padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                    bottom = 40.dp
                                )
                                .clickable { onEditScreen(pictureData) },
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 4.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = pictureData.comment.orEmpty()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .padding(bottom = 4.dp, end = 0.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { },
                                    onClick = {
                                        onRemove(pictureData)
                                        expanded = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    TopScreenContent(
        launchPicker = {},
        onUpPress = {},
        onEditScreen = {},
        pictures = emptyList(),
        onSaveData = {},
        onRemove = {},
    )
}
