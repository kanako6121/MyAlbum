package com.example.myalbum.core.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myalbum.R
import com.example.myalbum.main.MainNavOption

data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int,
)

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            MainNavOption.TopScreen,
            R.string.label_title,
            R.drawable.ic_launcher_foreground,
        ),
        AppDrawerItemInfo(
            MainNavOption.EditScreen,
            R.string.label_edit,
            R.drawable.ic_launcher_foreground,
        ),
        AppDrawerItemInfo(
            MainNavOption.PreviewScreen,
            R.string.label_preview,
            R.drawable.ic_launcher_foreground,
        )
    )
}