package com.example.myalbum.core.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myalbum.R

data class AppDrawerItem<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    @DrawableRes val drawbleId: (Any) -> Unit,
)

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItem(
            MainNavOption.TopScreen,
            R.string.label_top,
            R.drawable.ic_launcher_foreground,
        ),
        AppDrawerItem(
            MainNavOption.EditScreen,
            R.string.label_edit,
            R.drawable.ic_launcher_foreground,
        ),
        AppDrawerItem(
            MainNavOption.PreviewScreen,
            R.string.label_preview,
            R.drawable.ic_launcher_foreground,
        )
    )
}

enum class MainNavOption {
    TopScreen,
    EditScreen,
    PreviewScreen,
}