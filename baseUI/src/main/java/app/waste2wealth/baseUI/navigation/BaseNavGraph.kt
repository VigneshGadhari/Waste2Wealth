package app.waste2wealth.baseUI.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.baseNavGraph(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        exitTransition = {
            defaultExit()
        },
        enterTransition = {
            defaultEnter()
        },
        popEnterTransition = {
            defaultEnter()
        },
        popExitTransition = {
            defaultExit()
        }
    ) {
        content(it)
    }
}

fun defaultEnter(duration: Int = 300): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -duration },
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(animationSpec = tween(durationMillis = duration))
}

fun defaultExit(duration: Int = 300): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -duration },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(durationMillis = duration))
}
