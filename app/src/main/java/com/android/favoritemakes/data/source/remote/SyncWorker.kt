package com.android.favoritemakes.data.source.remote

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.android.favoritemakes.data.mappers.mapToModels
import com.android.favoritemakes.data.source.local.db.room.MakeDao
import com.android.favoritemakes.data.source.local.db.room.model.Make
import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.utilities.MAKES_DATA_FILENAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okio.BufferedSource
import okio.buffer
import okio.source

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val makeDao: MakeDao,
    private val moshi: Moshi,
) : CoroutineWorker(context, workerParams) {
    private inline fun <reified T> Moshi.parseList(source: BufferedSource): List<T>? {
        return adapter<List<T>>(
            Types.newParameterizedType(
                List::class.java,
                T::class.java
            )
        ).fromJson(source)
    }

    // Instead of hardcoded values, SyncService should be used in production version
    private fun fetchTopMakes() =
        applicationContext.assets.open(MAKES_DATA_FILENAME).source().buffer()

    private fun BufferedSource.parseResults() = moshi.parseList<MakeJson>(this)

    private fun List<Make>.saveLocally() {
        makeDao.insertAll(this)
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            fetchTopMakes().use { inputStream ->
                inputStream.parseResults()?.let { jsonObjects ->
                    jsonObjects.mapToModels().saveLocally()
                    simulateNetworkDelay()
                    Result.failure()
                } ?: run {
                    Log.e(TAG, "Sync error - cannot parse json")
                    Result.failure()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Sync error", ex)
            Result.failure()
        }
    }

    private suspend fun simulateNetworkDelay() {
        delay(SYNC_FAKE_DELAY)
    }

    companion object {
        private const val TAG = "SyncWorker"
        private const val SYNC_FAKE_DELAY = 2000L
    }
}