package com.example.myalbum.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myalbum.core.theme.MyAlbumTheme
import com.example.myalbum.feature.top.TopScreenContent
import com.example.myalbum.feature.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var topViewModel: TopViewModel
    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAlbumTheme {
                MainNav()
                TopScreenContent(
                    onUpPress = { /*TODO*/ },
                    onNavigationToEditScreen = { /*TODO*/ },
                    onNavigationToPreviewScreen = {},
                )
            }
        }
    }
}