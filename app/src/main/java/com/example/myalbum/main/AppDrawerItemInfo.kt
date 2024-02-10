package com.example.myalbum.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class AppDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    @DrawableRes val drawbleId: Int,
    @StringRes val descriptionId: Int
)