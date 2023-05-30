package app.waste2wealth.com.bottombar

import app.waste2wealth.com.R
import app.waste2wealth.com.navigation.Screens

sealed class BottomBarScreens(val route: String?, val title: String?, val icon: Int?) {
    object HomeScreen : BottomBarScreens(Screens.Dashboard.route, "Home", R.drawable.homei)
    object RewardsScreen : BottomBarScreens(Screens.Splash.route, "Rewards", R.drawable.rewardsi)
    object RecordScreen :
        BottomBarScreens(Screens.QrCodeScanner.route, "Record", R.drawable.recordsi)

    object CommunityScreen :
        BottomBarScreens(Screens.Community.route, "Community", R.drawable.commi)
}

val items = listOf(
    BottomBarScreens.HomeScreen,
    BottomBarScreens.RewardsScreen,
    BottomBarScreens.RecordScreen,
    BottomBarScreens.CommunityScreen
)
