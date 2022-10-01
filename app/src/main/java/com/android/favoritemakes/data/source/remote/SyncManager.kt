package com.android.favoritemakes.data.source.remote

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SyncManager @Inject constructor(
    @ApplicationContext val context: Context,
) {
    fun startSync(): Flow<SyncStatus> {
        val request = OneTimeWorkRequestBuilder<SyncWorker>().build()
        with(WorkManager.getInstance(context)) {
            enqueue(request)
            return getWorkInfoByIdLiveData(request.id).asFlow().map {
                when (it.state) {
                    WorkInfo.State.ENQUEUED,
                    WorkInfo.State.RUNNING -> SyncStatus.RUNNING
                    WorkInfo.State.SUCCEEDED -> SyncStatus.SUCCEEDED
                    WorkInfo.State.FAILED,
                    WorkInfo.State.BLOCKED,
                    WorkInfo.State.CANCELLED -> SyncStatus.FAILED
                }
            }.distinctUntilChanged()
        }
    }
}

enum class SyncStatus {
    IDLE, RUNNING, SUCCEEDED, FAILED,
}