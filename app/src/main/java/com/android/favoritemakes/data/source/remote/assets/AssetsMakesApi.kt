package com.android.favoritemakes.data.source.remote.assets

import android.content.Context
import com.android.favoritemakes.data.source.remote.retrofit.MakesApi
import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.utilities.MAKES_DATA_FILENAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.delay
import okio.BufferedSource
import okio.buffer
import okio.source
import java.io.IOException

// Simulates Remote API sync and fetches vehicle makes from assets.
// Instead of hardcoded values, retrofit.MakesApi should be used in production version.
class AssetsMakesApi constructor(
    private val context: Context,
    private val moshi: Moshi,
) : MakesApi {

    private fun fetchTopMakes(): BufferedSource =
        context.assets.open(MAKES_DATA_FILENAME).source().buffer()

    private fun BufferedSource.parseResults() = use { moshi.parseList<MakeJson>(this) }

    private inline fun <reified T> Moshi.parseList(source: BufferedSource): List<T>? {
        return adapter<List<T>>(
            Types.newParameterizedType(List::class.java, T::class.java)
        ).fromJson(source)
    }

    override suspend fun getVehicleMakes(listSize: Int): List<MakeJson> =
        fetchTopMakes().parseResults().also {
            simulateNetworkDelay()
        } ?: throw IOException("Failed to parse objects")

    private suspend fun simulateNetworkDelay() {
        delay(SYNC_FAKE_DELAY)
    }

    companion object {
        private const val SYNC_FAKE_DELAY = 2000L
    }
}