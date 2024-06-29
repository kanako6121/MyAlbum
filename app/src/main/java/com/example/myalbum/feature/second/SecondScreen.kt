package com.example.myalbum.feature.second

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SecondScreen(
    onUpPress: () -> Unit,
) {
    SecondScreenContent(
        onUpPress = onUpPress,
    )
}

@Composable
fun SecondScreenContent(
    onUpPress: () -> Unit,
) {
    var selectedPhoto by remember { mutableStateOf(-1) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                content = {
                    //  items() { index ->
                    //       Column(
                    //            modifier = Modifier
                    //              .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                    //               .shadow(elevation = 4.dp)
                    //.aspectRatio(1.5f, matchHeightConstraintsFirst = false)
                    //               .background(Color.White)
                    //               .border(
                    //                    BorderStroke(width = 0.5.dp, color = Color.Gray)
                    //               )
                    //              .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 24.dp),
                    //        ) {
                    //           Image(
                    //               painter = painterResource(),
                    //               contentDescription = "Photo",
                    //               contentScale = ContentScale.Fit
                    //            )
                    //             Text(text = "運動会")
                    //         }
                    //    }
                }
            )
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    label: String
) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onUpPress: () -> Unit,
) {

    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = null
            )
        },

        )
}

@Preview(showBackground = true)
@Composable
fun ShowPhotoGrid() {
    SecondScreen(
        onUpPress = {},
    )
}
