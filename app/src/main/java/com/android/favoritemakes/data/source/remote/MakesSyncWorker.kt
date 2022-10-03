package com.android.favoritemakes.data.source.remote

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.favoritemakes.data.mappers.mapToDBModels
import com.android.favoritemakes.data.source.local.db.MakeRepository
import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import com.android.favoritemakes.di.IoDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
class MakesSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val makeRepository: MakeRepository,
    private val syncRepository: SyncRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(context, workerParams) {

    private suspend fun List<MakeModel>.saveLocally() {
        makeRepository.insertAll(this)
    }

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        try {
            when (val apiSyncResult = syncRepository.getVehicleMakes()) {
                is com.android.favoritemakes.utilities.result.Result.Failure -> {
                    Log.e(TAG, "Sync error - ${apiSyncResult.message}")
                    Result.failure()
                }
                is com.android.favoritemakes.utilities.result.Result.Success -> {
                    apiSyncResult.result.mapToDBModels().saveLocally()
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Sync error", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SyncWorker"
    }
}