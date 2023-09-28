package app.waste2wealth.baseUI.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.waste2wealth.baseUI.utils.DefaultText

@Composable
fun <S> MovingText(
    modifier: Modifier = Modifier,
    targetState: S,
    label: String = "MovingText",
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform = {
        slideIntoContainer(
            towards = AnimatedContentScope.SlideDirection.Up,
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn() with slideOutOfContainer(
            towards = AnimatedContentScope.SlideDirection.Up,
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut()
    },
    contentAlignment: Alignment = Alignment.TopStart,
    text: String = "Search",
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            transitionSpec()
        },
        label = label,
        contentAlignment = contentAlignment,
    ) {target->
        DefaultText(
            text = "$text $target",
            modifier = modifier
                .fillMaxWidth()
        )
    }
}