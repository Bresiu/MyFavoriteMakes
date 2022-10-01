package com.android.favoritemakes.data.source.remote

import com.android.favoritemakes.data.source.remote.model.MakeJson
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path


interface SyncService {
    @GET("TODO/provide/real/endpoint/{listSize}")
    fun getVehicleMakes(
        @Path("listSize") listSize: Int = 100,
    ): Flow<List<MakeJson>>
}