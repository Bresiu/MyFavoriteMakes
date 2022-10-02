package com.android.favoritemakes.makeslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.imageLoader
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

@Composable
fun MakesListScreen(
    viewModel: MakesListViewModel = viewModel(),
) {
    val makes by viewModel.fetchMakes().collectAsState(emptyList())
    MakesList(makes = makes, viewModel.imageLoader)
}

@Composable
fun MakesList(
    makes: List<MakeState>,
    imageLoader: ImageLoader,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
    ) {
        items(makes) { make ->
            MakeRow(make = make, imageLoader = imageLoader)
        }
    }
}

@Composable
fun MakeRow(
    make: MakeState,
    imageLoader: ImageLoader,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RowItem(
            modifier = Modifier.height(60.dp),
            make = make,
            imageLoader = imageLoader
        )
    }
}

@Preview
@Composable
fun MakesListPreview() {
    FavoriteMakesTheme {
        MakesList(
            makes = listOf(
                MakeState(
                    id = 0,
                    name = "Toyota",
                    logoUrl = "",
                    countryFlag = "🇯🇵",
                    isFavorite = true,
                ), MakeState(
                    id = 0,
                    name = "BMW",
                    logoUrl = "",
                    countryFlag = "🇩🇪",
                    isFavorite = false,
                )
            ),
            imageLoader = LocalContext.current.imageLoader,
        )
    }
}