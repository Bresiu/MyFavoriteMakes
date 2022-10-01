package com.android.favoritemakes.makeslist

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.android.favoritemakes.data.source.local.db.room.MakeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MakesListViewModel @Inject constructor(
    private val makeDao: MakeDao,
    val imageLoader: ImageLoader,
) : ViewModel() {
    fun fetchMakes() = makeDao.getAll()
}