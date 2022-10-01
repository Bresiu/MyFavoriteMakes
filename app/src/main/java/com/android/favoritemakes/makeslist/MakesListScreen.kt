package com.android.favoritemakes.makeslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.android.favoritemakes.data.source.local.db.room.model.Make

@Composable
fun MakesListScreen(
    viewModel: MakesListViewModel = viewModel(),
) {
    val makes by viewModel.fetchMakes().collectAsState(emptyList())
    MakesList(makes = makes, viewModel.imageLoader)
}

@Composable
fun MakesList(
    makes: List<Make>,
    imageLoader: ImageLoader,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 6.dp)
    ) {
        items(makes) { make ->
            MakeRow(make = make, imageLoader = imageLoader)
        }
    }
}

@Composable
fun MakeRow(
    make: Make,
    imageLoader: ImageLoader,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.height(16.dp).padding(start = 10.dp),
                model = make.logoUrl, contentDescription = "",
                imageLoader = imageLoader,
            )

            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                text = make.name
            )
        }
    }
}
