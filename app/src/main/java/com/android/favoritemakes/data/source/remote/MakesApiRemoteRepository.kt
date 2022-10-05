package com.android.favoritemakes.data.source.remote

import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.data.source.remote.retrofit.MakesApi
import com.android.favoritemakes.utilities.result.Result
import java.io.IOException

class MakesApiRemoteRepository constructor(
    private val makesApi: MakesApi,
) : MakesRemoteRepository {

    override suspend fun getVehicleMakes(): Result<List<MakeJson>> {
        return try {
            val makes = makesApi.getVehicleMakes()
            Result.Success(makes)
        } catch (ioException: IOException) {
            Result.Failure("Failed to fetch objects; ${ioException.message}")
        }
    }
}