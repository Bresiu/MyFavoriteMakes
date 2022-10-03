package com.android.favoritemakes.data.source.remote

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.android.favoritemakes.coCalledOnce
import com.android.favoritemakes.coWasNotCalled
import com.android.favoritemakes.data.provideTestMakeJsonList
import com.android.favoritemakes.data.source.local.db.MakeRepository
import com.android.favoritemakes.mockkLogger
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
        val makeRepository: MakeRepository = mockk {
            coEvery { insertAll(any()) } just Runs
        }
        val syncRepository: SyncRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Success(provideTestMakeJsonList())
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makeRepository,
            syncRepository,
            UnconfinedTestDispatcher(testScheduler)
        )
        val result = makesSyncWorker.doWork()
        coCalledOnce { makeRepository.insertAll(any()) }
        coCalledOnce { syncRepository.getVehicleMakes() }
        confirmVerified(makeRepository, syncRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Success::class.java)
    }

    @Test
    fun `test sync repository returns failure`() = runTest {
        val makeRepository: MakeRepository = mockk()
        val syncRepository: SyncRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Failure("something went wrong")
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makeRepository,
            syncRepository,
            UnconfinedTestDispatcher(testScheduler)
        )
        val result = makesSyncWorker.doWork()
        coWasNotCalled { makeRepository.insertAll(any()) }
        coCalledOnce { syncRepository.getVehicleMakes() }
        confirmVerified(makeRepository, syncRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Failure::class.java)
    }

    @Test
    fun `test make repository throws exception`() = runTest {
        val makeRepository: MakeRepository = mockk {
            coEvery { insertAll(any()) } throws IllegalAccessException()
        }
        val syncRepository: SyncRepository = mockk {
            coEvery { getVehicleMakes() } returns Result.Success(provideTestMakeJsonList())
        }
        val makesSyncWorker = MakesSyncWorker(
            context,
            workerParameters,
            makeRepository,
            syncRepository,
            UnconfinedTestDispatcher(testScheduler),
        )
        val result = makesSyncWorker.doWork()
        coCalledOnce { makeRepository.insertAll(any()) }
        coCalledOnce { syncRepository.getVehicleMakes() }
        confirmVerified(makeRepository, syncRepository)
        Assertions.assertThat(result).isInstanceOf(ListenableWorker.Result.Failure::class.java)
    }
}