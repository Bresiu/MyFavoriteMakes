package com.android.favoritemakes.makeslist

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.favoritemakes.R
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    Crossfade(targetState = isFavorite) { isChecked ->
        IconButton(onClick = onClick) {
            if (isChecked) {
                Icon(
                    modifier = modifier,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_favorite),
                    contentDescription = stringResource(R.string.unfavorite_make_content_description),
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    modifier = modifier,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_favorite_border),
                    contentDescription = stringResource(R.string.favorite_make_content_description),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoriteIconPreview() {
    FavoriteMakesTheme {
        FavoriteIcon(isFavorite = true) {}
    }
}