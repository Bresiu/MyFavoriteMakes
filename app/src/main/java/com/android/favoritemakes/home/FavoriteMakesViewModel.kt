package com.android.favoritemakes.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.favoritemakes.data.source.local.db.room.MakeDao
import com.android.favoritemakes.data.source.remote.SyncManager
import com.android.favoritemakes.data.source.remote.SyncStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMakesViewModel @Inject constructor(
    private val syncManager: SyncManager,
    private val makeDao: MakeDao,
) : ViewModel() {
    private val _favoriteMakesCount = mutableStateOf(0)
    val favoriteMakes: State<Int>
        get() = _favoriteMakesCount
    private val _syncStatus = mutableStateOf(SyncStatus.IDLE)
    val syncStatus: State<SyncStatus>
        get() = _syncStatus

    init {
        fetchFavoritesCount()
        startInitialSyncIfNeeded()
    }

    private fun fetchFavoritesCount() {
        viewModelScope.launch {
            makeDao.getFavoritesCount().collect {
                _favoriteMakesCount.value = it
            }
        }
    }

    private fun startInitialSyncIfNeeded() {
        viewModelScope.launch {
            syncManager.startSync().collect {
                _syncStatus.value = it
                if (it == SyncStatus.FAILED) {
                    delay(2000)
                    _syncStatus.value = SyncStatus.IDLE
                }
            }
        }
    }
}