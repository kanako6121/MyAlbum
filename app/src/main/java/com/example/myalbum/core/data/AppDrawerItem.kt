package com.example.myalbum.core.data

data class AppDrawerItem(
    val title: String,
)

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItem(
            MainNavOption.TopScreen.toString(),
        ),
        AppDrawerItem(
            MainNavOption.EditScreen.toString(),
        ),
        AppDrawerItem(
            MainNavOption.PreviewScreen.toString(),
        )
    )
}

enum class MainNavOption {
    TopScreen,
    EditScreen,
    PreviewScreen,
}
