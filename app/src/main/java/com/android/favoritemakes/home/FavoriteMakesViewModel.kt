package com.android.favoritemakes.home

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.favoritemakes.data.source.local.db.MakeRepository
import com.android.favoritemakes.data.source.remote.SyncManager
import com.android.favoritemakes.data.source.remote.SyncStatus
import com.android.favoritemakes.di.IoDispatcher
import com.android.favoritemakes.utilities.extension.SharedPreferencesBooleanDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMakesViewModel @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val syncManager: SyncManager,
    makeRepository: MakeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private var isBeforeInitialSync: Boolean by SharedPreferencesBooleanDelegate(
        sharedPreferences,
        true
    )
    val favoriteMakes: Flow<Int> = makeRepository.getFavoritesCount()
    private val _syncStatus = mutableStateOf(SyncStatus.IDLE)
    val syncStatus: State<SyncStatus>
        get() = _syncStatus

    init {
        startInitialSyncIfNeeded()
    }

    private fun startInitialSyncIfNeeded() {
        if (isBeforeInitialSync) {
            viewModelScope.launch(ioDispatcher) {
                syncManager.startSync().collect {
                    _syncStatus.value = it
                    if (it == SyncStatus.FAILED) {
                        delay(IDLE_DELAY_AFTER_FAILURE)
                        _syncStatus.value = SyncStatus.IDLE
                    }
                    if (it == SyncStatus.SUCCEEDED) {
                        isBeforeInitialSync = false
                    }
                }
            }
        }
    }

    companion object {
        private const val IDLE_DELAY_AFTER_FAILURE = 2000L
    }
}