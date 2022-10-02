package com.android.favoritemakes.makeslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    make: MakeState,
    imageLoader: ImageLoader = LocalContext.current.imageLoader,
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
        Column(
            Modifier.padding(start = 16.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                text = make.name
            )
            Text(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                text = make.countryFlag,
            )
        }
    }
}

data class MakeState(
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
            make = MakeState(
                id = 0,
                name = "Toyota",
                logoUrl = "",
                countryFlag = "🇯🇵",
                isFavorite = true,
            )
        )
    }
}