package com.android.favoritemakes.data.source.remote

import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.utilities.result.Result

interface MakesRemoteRepository {
    suspend fun getVehicleMakes(): Result<List<MakeJson>>
}