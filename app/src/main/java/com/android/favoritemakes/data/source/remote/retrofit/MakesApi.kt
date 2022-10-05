package com.android.favoritemakes.data.source.remote.retrofit

import com.android.favoritemakes.data.source.remote.model.MakeJson
import retrofit2.http.GET
import retrofit2.http.Path

interface MakesApi {
    @GET("TODO/provide/real/endpoint/{listSize}")
    suspend fun getVehicleMakes(
        @Path("listSize") listSize: Int = 100,
    ): List<MakeJson>
}