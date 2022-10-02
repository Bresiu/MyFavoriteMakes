package com.android.favoritemakes.makeslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.imageLoader
import com.android.favoritemakes.R
import com.android.favoritemakes.ui.theme.FavoriteMakesTheme

@Composable
fun MakesListScreen(
    viewModel: MakesListViewModel = viewModel(),
) {
    val makes: List<MakeData> by viewModel.fetchMakes().collectAsState(initial = emptyList())
    MakesList(makes = makes, viewModel.imageLoader) { makeId, isFavorite ->
        viewModel.toggleFavoriteMake(makeId, isFavorite)
    }
}

@Composable
fun MakesList(
    makes: List<MakeData>,
    imageLoader: ImageLoader,
    onFavoriteIconToggle: (Long, Boolean) -> Unit,
) {
    if (makes.isEmpty()) {
        EmptyState()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
    ) {
        items(
            items = makes,
            key = { it.id },
        ) { make ->
            MakeRow(make = make, imageLoader = imageLoader) {
                onFavoriteIconToggle(make.id, make.isFavorite)
            }
        }
    }
}

@Composable
fun EmptyState() {
    Text(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.no_items_empty_state),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun MakeRow(
    make: MakeData,
    imageLoader: ImageLoader,
    onFavoriteIconClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RowItem(
            modifier = Modifier.height(60.dp),
            make = make,
            imageLoader = imageLoader,
            onFavoriteIconClick,
        )
    }
}

@Preview
@Composable
fun MakesListPreview() {
    FavoriteMakesTheme {
        MakesList(
            makes = listOf(
                MakeData(
                    id = 0,
                    name = "Toyota",
                    logoUrl = "",
                    countryFlag = "🇯🇵",
                    isFavorite = true,
                ), MakeData(
                    id = 1,
                    name = "BMW",
                    logoUrl = "",
                    countryFlag = "🇩🇪",
                    isFavorite = false,
                )
            ),
            imageLoader = LocalContext.current.imageLoader,
            onFavoriteIconToggle = { _, _ -> }
        )
    }
}

@Preview
@Composable
fun EmptyStatePreview() {
    FavoriteMakesTheme {
        EmptyState()
    }
}