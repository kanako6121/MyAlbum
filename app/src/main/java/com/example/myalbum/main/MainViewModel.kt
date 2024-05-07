package com.example.myalbum.main

import android.arch.lifecycle.ViewModel
import android.provider.ContactsContract.Contacts.Photo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.channels.Channel

class MainViewModel(private val photo: Photo): ViewModel(){
    val photoIntent = Channel<MainIntent>(Channel.UNLIMITED)
    var state = mutableStateOf<MainState>(MainState.Idle)
    private set

    init {
        handleIntent()
    }
    private fun handleIntent() {
        viewModelSc
    }
}

