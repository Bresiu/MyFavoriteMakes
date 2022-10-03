package com.android.favoritemakes.data.source.remote.assets

import android.content.Context
import com.android.favoritemakes.data.source.remote.SyncRepository
import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.utilities.MAKES_DATA_FILENAME
import com.android.favoritemakes.utilities.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.delay
import okio.BufferedSource
import okio.buffer
import okio.source
import java.io.IOException

// Simulates Remote API sync and fetches vehicle makes from assets
// Instead of hardcoded values, SyncService should be used in production version
class AssetsSyncRepository constructor(
    private val context: Context,
    private val moshi: Moshi,
) : SyncRepository {

    private fun fetchTopMakes(): BufferedSource =
        context.assets.open(MAKES_DATA_FILENAME).source().buffer()

    private fun BufferedSource.parseResults() = moshi.parseList<MakeJson>(this)

    private inline fun <reified T> Moshi.parseList(source: BufferedSource): List<T>? {
        return adapter<List<T>>(
            Types.newParameterizedType(List::class.java, T::class.java)
        ).fromJson(source)
    }

    override suspend fun getVehicleMakes(): Result<List<MakeJson>> {
        return try {
            simulateNetworkDelay()
            fetchTopMakes().use { it.parseResults() }?.let {
                Result.Success(it)
            } ?: Result.Failure("Failed to parse objects")
        } catch (ioException: IOException) {
            Result.Failure("Failed to fetch objects")
        }
    }

    private suspend fun simulateNetworkDelay() {
        delay(SYNC_FAKE_DELAY)
    }

    companion object {
        private const val SYNC_FAKE_DELAY = 2000L
    }
}