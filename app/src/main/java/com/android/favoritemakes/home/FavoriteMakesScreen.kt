package com.android.favoritemakes.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.favoritemakes.R
import com.android.favoritemakes.data.source.remote.SyncStatus
import com.android.favoritemakes.ui.animations.INDICATOR_SIZE
import com.android.favoritemakes.ui.animations.LoadingIndicator
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme
import kotlinx.coroutines.delay

private const val NUDGE_DELAY = 300L

@Composable
fun FavoriteMakesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMakesViewModel = viewModel(),
) {
    var isNudgeVisible by rememberSaveable { mutableStateOf(false) }
    val favoriteMakesCount by remember { viewModel.favoriteMakes }
    val nudgeText = stringResource(
        id = if (favoriteMakesCount == 0) {
            R.string.nudge_favorite_first_make
        } else {
            R.string.nudge_favorite_more_makes
        }
    )
    val syncStatus by remember { viewModel.syncStatus }
    val onNudgeClick: () -> Unit = { Log.d("BRS", "on nudge click") }
    LaunchedEffect(isNudgeVisible) {
        delay(NUDGE_DELAY)
        isNudgeVisible = true
    }
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.height(INDICATOR_SIZE.dp)
            ) {
                LoadingIndicator(
                    indicatorHeight = INDICATOR_SIZE,
                    isVisible = syncStatus == SyncStatus.RUNNING || syncStatus == SyncStatus.FAILED,
                    color = if (syncStatus == SyncStatus.RUNNING) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.favorite_makes_title),
                style = typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Box {
                Icon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_favorite),
                    contentDescription = stringResource(R.string.heart_icon_content_description),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = favoriteMakesCount.toString(),
                    color = Color.White,
                    fontSize = 120.sp
                )
            }
            AnimatedNudge(
                isVisible = isNudgeVisible,
                text = nudgeText,
                onClick = onNudgeClick,
            )
        }
    }
}

private const val animationDuration = 300

@Composable
fun AnimatedNudge(
    isVisible: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = animationDuration)
        ) { fullWidth -> -fullWidth / 3 } + fadeIn(
            animationSpec = tween(durationMillis = animationDuration)
        ),
    ) {
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = onClick,
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
                text = text,
                style = typography.labelMedium,
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    FavoriteMakesTheme {
        FavoriteMakesScreen()
    }
}

