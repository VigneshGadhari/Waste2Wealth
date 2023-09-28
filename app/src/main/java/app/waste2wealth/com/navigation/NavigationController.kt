package app.waste2wealth.com.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.waste2wealth.baseUI.constants.Screens
import app.waste2wealth.baseUI.navigation.baseNavGraph
import app.waste2wealth.com.UserDatastore
import app.waste2wealth.com.collectwaste.CollectWaste
import app.waste2wealth.com.collectwaste.CollectWasteInfo
import app.waste2wealth.com.collectwaste.SuccessfullyCollected
import app.waste2wealth.com.communities.ui.CommunitiesSection
import app.waste2wealth.com.dashboard.NewDashboard
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.login.CompleteProfile
import app.waste2wealth.com.login.LoginPage
import app.waste2wealth.com.login.onboarding.Onboarding
import app.waste2wealth.com.login.onboarding.SettingUp
import app.waste2wealth.com.profile.NewProfileScreen
import app.waste2wealth.com.reportwaste.ReportWaste
import app.waste2wealth.com.rewards.ClaimedRewardsScreen
import app.waste2wealth.com.rewards.NewRewardsScreen
import app.waste2wealth.com.rewards.RewardDetails
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController(
    scaffoldState: ScaffoldState,
    locationViewModel: LocationViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val viewModel: LocationViewModel = hiltViewModel()
    val context = LocalContext.current
    val store = UserDatastore(context)
    val email = store.getEmail.collectAsState(initial = "")
    val profile = store.getPfp.collectAsState(initial = "")
    val name = store.getName.collectAsState(initial = "")
    AnimatedNavHost(navController = navController, startDestination = Screens.Splash.route) {
        baseNavGraph(route = Screens.LoginScreen.route) {
            LoginPage(navController = navController, scaffoldState = scaffoldState)
        }
        baseNavGraph(route = Screens.Dashboard.route) {
            NewDashboard(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value,
                viewModel = viewModel
            )
        }
        baseNavGraph(Screens.Profile.route) {
            NewProfileScreen(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        baseNavGraph(Screens.Onboarding.route) {
            Onboarding(navHostController = navController)
        }
        baseNavGraph(Screens.CompleteProfile.route) {
            CompleteProfile(navHostController = navController)
        }
        baseNavGraph(Screens.SettingUp.route) {
            SettingUp(navHostController = navController)

        }
        baseNavGraph(Screens.Community.route) {
            CommunitiesSection(
                paddingValues = paddingValues,
                email = email.value,
                name = name.value,
            )
        }
        baseNavGraph(Screens.ReportWaste.route) {
            ReportWaste(
                navController = navController, viewModel = viewModel,
                email = email.value,
                name = name.value,
                pfp = profile.value,
            )
        }
        baseNavGraph(Screens.CollectWasteLists.route) {
            CollectWaste(
                navController = navController,
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
        baseNavGraph(Screens.CollectWasteInfo.route) {
            CollectWasteInfo(navController = navController, viewModel = viewModel)
        }
        baseNavGraph(Screens.CollectedWasteSuccess.route) {
            SuccessfullyCollected(
                navController = navController, viewModel = viewModel,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        baseNavGraph(Screens.Rewards.route) {
            NewRewardsScreen(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value,
                viewModel = viewModel
            )
        }
        baseNavGraph(Screens.RewardsDetails.route) {
            RewardDetails(
                navController = navController,
                email = email.value,
                viewModel = viewModel,
                name = name.value,
            )
        }
        baseNavGraph(Screens.ClaimedRewards.route) {
            ClaimedRewardsScreen(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value,
                viewModel = viewModel
            )
        }
        baseNavGraph(Screens.Splash.route) {
            SplashScreen(navController = navController, email = email.value)
        }

    }
}


