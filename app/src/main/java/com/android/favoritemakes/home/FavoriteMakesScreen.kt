package com.android.favoritemakes.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.favoritemakes.R
import com.android.favoritemakes.data.source.remote.SyncStatus
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme
import kotlinx.coroutines.delay

private const val BUTTON_DISPLAY_DELAY = 300L

@Composable
fun FavoriteMakesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMakesViewModel = viewModel(),
    navigateToMakesList: () -> Unit
) {
    val favoriteMakesCount by viewModel.favoriteMakes.collectAsState(initial = 0)
    var isButtonVisible by rememberSaveable { mutableStateOf(false) }
    val syncStatus by remember { viewModel.syncStatus }
    LaunchedEffect(isButtonVisible) {
        delay(BUTTON_DISPLAY_DELAY)
        isButtonVisible = true
    }
    Surface(modifier = modifier.fillMaxSize()) {
        FavoriteMakesContent(
            favoriteMakesCount = favoriteMakesCount.toString(),
            buttonState = ButtonState(
                isVisible = isButtonVisible,
                text = stringResource(
                    if (favoriteMakesCount == 0) {
                        R.string.label_favorite_first_make
                    } else {
                        R.string.label_favorite_more_makes
                    }
                ),
                onClick = navigateToMakesList,
            ),
            syncState = SyncState(
                isVisible = syncStatus == SyncStatus.RUNNING || syncStatus == SyncStatus.FAILED,
                isProgressVisible = syncStatus == SyncStatus.RUNNING,
                backgroundColor = when (syncStatus) {
                    SyncStatus.RUNNING -> MaterialTheme.colorScheme.primary
                    SyncStatus.FAILED -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.tertiary
                },
                text = when (syncStatus) {
                    SyncStatus.RUNNING -> stringResource(R.string.sync_status_syncing)
                    SyncStatus.FAILED -> stringResource(R.string.sync_status_error)
                    else -> ""
                },
            )
        )
    }
}

@Composable
fun FavoriteMakesContent(
    favoriteMakesCount: String,
    buttonState: ButtonState,
    syncState: SyncState,
) {
    Box(contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
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
                    text = favoriteMakesCount,
                    style = typography.displayLarge,
                    color = Color.White,
                )
            }
            AnimatedButton(buttonState = buttonState)
        }
        AnimatedSyncStatus(syncState = syncState)
    }
}

@Preview
@Composable
fun FavoriteMakesPreview() {
    FavoriteMakesTheme {
        FavoriteMakesContent(
            favoriteMakesCount = "3",
            buttonState = ButtonState(
                isVisible = true,
                text = stringResource(id = R.string.label_favorite_first_make),
                onClick = {}
            ),
            syncState = SyncState(
                isVisible = true,
                isProgressVisible = true,
                backgroundColor = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.sync_status_syncing)
            ),
        )
    }
}