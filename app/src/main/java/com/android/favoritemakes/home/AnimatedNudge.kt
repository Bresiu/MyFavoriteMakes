package com.android.favoritemakes.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.favoritemakes.R
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

private const val ANIMATION_DELAY = 300

@Composable
fun AnimatedNudge(
    nudgeState: NudgeState,
) {
    AnimatedVisibility(
        visible = nudgeState.isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        ) { fullWidth -> -fullWidth / 3 } + fadeIn(
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        ),
    ) {
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = nudgeState.onClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_car),
                contentDescription = stringResource(R.string.car_icon_content_description),
                tint = Color.White,
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = nudgeState.text,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview
@Composable
fun AnimatedNudgePreview() {
    FavoriteMakesTheme {
        AnimatedNudge(
            nudgeState = NudgeState(
                isVisible = true,
                text = stringResource(id = R.string.nudge_favorite_first_make),
                onClick = {}
            ),
        )
    }
}

data class NudgeState(
    val isVisible: Boolean,
    val text: String,
    val onClick: () -> Unit,
)