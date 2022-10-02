package com.android.favoritemakes.makeslist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import com.android.favoritemakes.R
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

private const val CAR_LOGO_SIZE = 60

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    make: MakeData,
    imageLoader: ImageLoader = LocalContext.current.imageLoader,
    onFavoriteIconClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(CAR_LOGO_SIZE.dp),
            placeholder = painterResource(R.drawable.ic_car),
            model = make.logoUrl,
            contentDescription = stringResource(R.string.make_logo_content_description),
            imageLoader = imageLoader,
            error = painterResource(R.drawable.ic_car),
        )
        Row {
            Text(
                modifier = Modifier.padding(start = 18.dp, end = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                text = make.name
            )
            Text(
                modifier = Modifier.align(alignment = Alignment.Top),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                text = make.countryFlag,
            )
        }
        Spacer(Modifier.weight(1f))
        FavoriteIcon(
            modifier = Modifier,
            isFavorite = make.isFavorite,
            onClick = onFavoriteIconClick,
        )
    }
}

data class MakeData(
    val id: Long,
    val name: String,
    val logoUrl: String,
    val countryFlag: String,
    val isFavorite: Boolean,
)

@Preview
@Composable
fun RowItemPreview() {
    FavoriteMakesTheme {
        RowItem(
            make = MakeData(
                id = 0,
                name = "Toyota",
                logoUrl = "",
                countryFlag = "🇯🇵",
                isFavorite = true,
            ),
            onFavoriteIconClick = {},
        )
    }
}