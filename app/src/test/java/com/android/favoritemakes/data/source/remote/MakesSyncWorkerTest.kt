package com.android.favoritemakes.data.source.remote

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.android.favoritemakes.utils.coCalledOnce
import com.android.favoritemakes.utils.coWasNotCalled
import com.android.favoritemakes.data.provideTestMakesJsonList
import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import com.android.favoritemakes.utils.mockkLogger
import com.android.favoritemakes.utilities.result.Result
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MakesSyncWorkerTest {

    private lateinit var context: Context
    private lateinit var workerParameters: WorkerParameters

    @Before
    fun setUp() {
        context = mockk()
        workerParameters = mockk {
            every { taskExecutor } returns mockk {
                every { backgroundExecutor } returns mockk()
            }
        }
        mockkLogger()
    }

    @Test
    fun `test sync repository returns items`() = runTest {
        val makesLocalRepository: MakesLocalRepository = mockk {
            coEvery { insertAll(any()) } just Runs
        }
        val makesRemoteRepository: MakesRemoteRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Success(provideTestMakesJsonList())
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makesLocalRepository,
            makesRemoteRepository,
            UnconfinedTestDispatcher(testScheduler)
        )
        val result = makesSyncWorker.doWork()
        coCalledOnce { makesLocalRepository.insertAll(any()) }
        coCalledOnce { makesRemoteRepository.getVehicleMakes() }
        confirmVerified(makesLocalRepository, makesRemoteRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Success::class.java)
    }

    @Test
    fun `test sync repository returns failure`() = runTest {
        val makesLocalRepository: MakesLocalRepository = mockk()
        val makesRemoteRepository: MakesRemoteRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Failure("something went wrong")
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makesLocalRepository,
            makesRemoteRepository,
            UnconfinedTestDispatcher(testScheduler)
        )
        val result = makesSyncWorker.doWork()
        coWasNotCalled { makesLocalRepository.insertAll(any()) }
        coCalledOnce { makesRemoteRepository.getVehicleMakes() }
        confirmVerified(makesLocalRepository, makesRemoteRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Failure::class.java)
    }

    @Test
    fun `test make repository throws exception`() = runTest {
        val makesLocalRepository: MakesLocalRepository = mockk {
            coEvery { insertAll(any()) } throws IllegalAccessException()
        }
        val makesRemoteRepository: MakesRemoteRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Success(provideTestMakesJsonList())
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makesLocalRepository,
            makesRemoteRepository,
            UnconfinedTestDispatcher(testScheduler),
        )
        val result = makesSyncWorker.doWork()
        coCalledOnce { makesLocalRepository.insertAll(any()) }
        coCalledOnce { makesRemoteRepository.getVehicleMakes() }
        confirmVerified(makesLocalRepository, makesRemoteRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Failure::class.java)
    }
}