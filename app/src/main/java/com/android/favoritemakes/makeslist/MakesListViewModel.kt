package com.android.favoritemakes.makeslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.android.favoritemakes.data.mappers.mapToUIModels
import com.android.favoritemakes.data.source.local.db.MakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesListViewModel @Inject constructor(
    private val makeRepository: MakeRepository,
    val imageLoader: ImageLoader,
) : ViewModel() {
    fun fetchMakes(): Flow<List<MakeData>> = makeRepository.getAll().map { it.mapToUIModels() }

    fun toggleFavoriteMake(makeId: Long, currentFavoriteState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentFavoriteState) {
                makeRepository.unfavoriteMake(makeId)
            } else {
                makeRepository.favoriteMake(makeId)
            }
        }
    }
}