package com.android.favoritemakes.makeslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.android.favoritemakes.data.mappers.mapToUIModels
import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import com.android.favoritemakes.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesListViewModel @Inject constructor(
    private val makesLocalRepository: MakesLocalRepository,
    val imageLoader: ImageLoader,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    fun fetchMakes(): Flow<List<MakeData>> = makesLocalRepository.getAll().map { it.mapToUIModels() }

    fun toggleFavoriteMake(makeId: Long, currentFavoriteState: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            if (currentFavoriteState) {
                makesLocalRepository.unfavoriteMake(makeId)
            } else {
                makesLocalRepository.favoriteMake(makeId)
            }
        }
    }
}