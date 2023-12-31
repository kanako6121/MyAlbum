package com.example.myalbum

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myalbum.ui.theme.MyAlbumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAlbumTheme(dynamicColor = false) {
                PhotoScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoScreen() {
    val photos = listOf(
        R.drawable.seigo1,
        R.drawable.seigo2,
        R.drawable.seigo3,
        R.drawable.seigo4,
        R.drawable.seigo5,
        R.drawable.seigo7,
        R.drawable.seigo9,
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_title),
            )
        }
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
            PhotoScreenContent(
                photos = photos
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
) {
    TopAppBar(
        navigationIcon = {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
        },
        title = { Text(text = stringResource(id = R.string.label_title)) },
    )
}


@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    PhotoScreen()
}