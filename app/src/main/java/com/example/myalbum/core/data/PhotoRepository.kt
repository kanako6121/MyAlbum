package com.example.myalbum.core.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {

}
