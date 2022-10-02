package com.android.favoritemakes.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.favoritemakes.R
import com.android.favoritemakes.ui.animations.LoadingIndicator
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

private const val ANIMATION_DELAY = 300

@Composable
fun AnimatedSyncStatus(
    modifier: Modifier = Modifier,
    syncState: SyncState,
) {
    AnimatedVisibility(
        visible = syncState.isVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        ) { fullHeight -> -fullHeight / 3 } + fadeIn(
            animationSpec = tween(durationMillis = ANIMATION_DELAY)
        ),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = syncState.backgroundColor, shape = RoundedCornerShape(20.dp))
                .animateContentSize(),
        ) {
            Text(
                text = syncState.text,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(20.dp, 10.dp),
            )
            LoadingIndicator(
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp),
                isVisible = syncState.isProgressVisible,
            )
        }
    }
}

@Preview
@Composable
fun AnimatedSyncStatusPreview() {
    FavoriteMakesTheme {
        AnimatedSyncStatus(
            syncState = SyncState(
                isVisible = true,
                isProgressVisible = true,
                backgroundColor = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.sync_status_syncing)
            )
        )
    }
}

data class SyncState(
    val isVisible: Boolean,
    val isProgressVisible: Boolean,
    val backgroundColor: Color,
    val text: String,
)