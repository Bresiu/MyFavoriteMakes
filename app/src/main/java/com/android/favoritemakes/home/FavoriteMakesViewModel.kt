package com.android.favoritemakes.home

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.favoritemakes.data.source.local.db.room.MakeDao
import com.android.favoritemakes.data.source.remote.SyncManager
import com.android.favoritemakes.data.source.remote.SyncStatus
import com.android.favoritemakes.utilities.extension.SharedPreferencesBooleanDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMakesViewModel @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val syncManager: SyncManager,
    private val makeDao: MakeDao,
) : ViewModel() {
    private var beforeInitialSync: Boolean by SharedPreferencesBooleanDelegate(sharedPreferences, true)
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
        if (beforeInitialSync) {
            viewModelScope.launch {
                syncManager.startSync().collect {
                    _syncStatus.value = it
                    if (it == SyncStatus.FAILED) {
                        delay(IDLE_DELAY_AFTER_FAILURE)
                        _syncStatus.value = SyncStatus.IDLE
                    }
                    if (it == SyncStatus.SUCCEEDED) {
                        beforeInitialSync = false
                    }
                }
            }
        }
    }

    companion object {
        private const val IDLE_DELAY_AFTER_FAILURE = 2000L
    }
}