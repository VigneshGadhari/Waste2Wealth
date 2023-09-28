package app.waste2wealth.baseUI.constants

import app.waste2wealth.baseUI.R

sealed class BottomBarScreens(val route: String?, val title: String?, val icon: Int?) {
    object HomeScreen : BottomBarScreens(Screens.Dashboard.route, "Home", R.drawable.homei)

    object CommunityScreen :
        BottomBarScreens(Screens.Community.route, "Community", R.drawable.commi)
}

val BottomBarItems = listOf(
    BottomBarScreens.HomeScreen,
    BottomBarScreens.CommunityScreen
)
