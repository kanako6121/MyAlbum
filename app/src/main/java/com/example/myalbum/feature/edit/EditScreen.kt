package com.example.myalbum.feature.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myalbum.R
import com.example.myalbum.feature.top.TopViewModel

@Composable
fun EditScreen(
    viewModel: TopViewModel,
    onNavigateTopScreen: () -> Unit,
    onNavigatePreviewScreen: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    EditContentScreen(
        onNavigateUp = onNavigateUp,
    )
}

@Composable
fun EditContentScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EditTopBar(
                title = stringResource(id = R.string.label_edit),
                onNavigateUp = onNavigateUp,
            )
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddings)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(ratio = 1.5f, matchHeightConstraintsFirst = false)
                    .align(Alignment.CenterHorizontally)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.seigo7),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(
    title: String,
    onNavigateUp: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = onUpPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}