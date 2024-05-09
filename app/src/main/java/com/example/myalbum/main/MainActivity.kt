package com.example.myalbum.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.top.TopScreenContent
import com.example.myalbum.feature.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TopViewModel>()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.savePhoto(uri)
        }
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContent {
                    MyAlbumTheme {
                        MainNav()
                        TopScreenContent(
                            selectUri = "",
                            onUpPress = { /*TODO*/ },
                            onNavigationToEditScreen = { /*TODO*/ },
                            onNavigationToPreviewScreen = {},
                            onClick = {},
                        )
                    }
                }
            }
        }