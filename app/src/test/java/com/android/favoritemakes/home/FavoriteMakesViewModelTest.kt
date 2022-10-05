package com.android.favoritemakes.home

import android.content.SharedPreferences
import app.cash.turbine.test
import com.android.favoritemakes.utils.coCalledOnce
import com.android.favoritemakes.utils.coWasNotCalled
import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import com.android.favoritemakes.data.source.remote.SyncManager
import com.android.favoritemakes.data.source.remote.SyncStatus
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class FavoriteMakesViewModelTest {
    @Test
    fun `favorite makes update test`() = runTest {
        val sharedPreferences: SharedPreferences = mockk {
            every { getBoolean("isBeforeInitialSync", true) } returns false
        }
        val syncManager: SyncManager = mockk()
        val makesLocalRepository: MakesLocalRepository = mockk {
            every { getFavoritesCount() } returns flowOf(0, 1, 2, 3)
        }
        val favoriteMakesViewModel = FavoriteMakesViewModel(
            sharedPreferences, syncManager, makesLocalRepository, UnconfinedTestDispatcher(testScheduler)
        )
        favoriteMakesViewModel.favoriteMakes.test {
            assertThat(awaitItem()).isEqualTo(0)
            assertThat(awaitItem()).isEqualTo(1)
            assertThat(awaitItem()).isEqualTo(2)
            assertThat(awaitItem()).isEqualTo(3)
            awaitComplete()
        }
    }

    @Test
    fun `start initial sync not needed test`() = runTest {
        val sharedPreferences: SharedPreferences = mockk {
            every { getBoolean("isBeforeInitialSync", true) } returns false
        }
        val syncManager: SyncManager = mockk()
        val makesLocalRepository: MakesLocalRepository = mockk {
            every { getFavoritesCount() } returns flowOf(0)
        }
        val favoriteMakesViewModel = FavoriteMakesViewModel(
            sharedPreferences, syncManager, makesLocalRepository, UnconfinedTestDispatcher(testScheduler)
        )
        coWasNotCalled { syncManager.startSync() }
        confirmVerified(syncManager)
        assertThat(favoriteMakesViewModel.syncStatus.value).isEqualTo(SyncStatus.IDLE)
    }

    @Test
    fun `is before initial sync - Sync succeeded test`() = runTest {
        val sharedPreferencesEditor: SharedPreferences.Editor = mockk {
            every { putBoolean("isBeforeInitialSync", false) } returns this
            every { apply() } just Runs
        }
        val sharedPreferences: SharedPreferences = mockk {
            every { getBoolean("isBeforeInitialSync", true) } returns true
            every { edit() } returns sharedPreferencesEditor
        }
        val syncManager: SyncManager = mockk {
            every { startSync() } returns flowOf(SyncStatus.SUCCEEDED)
        }
        val makesLocalRepository: MakesLocalRepository = mockk {
            every { getFavoritesCount() } returns flowOf(0)
        }
        val favoriteMakesViewModel = FavoriteMakesViewModel(
            sharedPreferences, syncManager, makesLocalRepository, UnconfinedTestDispatcher(testScheduler)
        )
        assertThat(favoriteMakesViewModel.syncStatus.value)
            .isEqualTo(SyncStatus.SUCCEEDED)
        coCalledOnce { syncManager.startSync() }
        coCalledOnce { sharedPreferences.getBoolean("isBeforeInitialSync", true) }
        coCalledOnce { sharedPreferences.edit() }
        coCalledOnce { sharedPreferencesEditor.putBoolean("isBeforeInitialSync", false) }
        coCalledOnce { sharedPreferencesEditor.apply() }
        confirmVerified(syncManager, sharedPreferences, sharedPreferencesEditor)
    }

    @Test
    fun `is before initial sync - Sync failed test`() = runTest {
        val sharedPreferences: SharedPreferences = mockk {
            every { getBoolean("isBeforeInitialSync", true) } returns true
        }
        val syncManager: SyncManager = mockk {
            every { startSync() } returns flowOf(SyncStatus.FAILED)
        }
        val makesLocalRepository: MakesLocalRepository = mockk {
            every { getFavoritesCount() } returns flowOf(0)
        }
        val favoriteMakesViewModel = FavoriteMakesViewModel(
            sharedPreferences, syncManager, makesLocalRepository, UnconfinedTestDispatcher(testScheduler)
        )
        assertThat(favoriteMakesViewModel.syncStatus.value)
            .isEqualTo(SyncStatus.FAILED)
        delay(2000L)
        assertThat(favoriteMakesViewModel.syncStatus.value)
            .isEqualTo(SyncStatus.IDLE)
        coCalledOnce { syncManager.startSync() }
        coCalledOnce { sharedPreferences.getBoolean("isBeforeInitialSync", true) }
        coWasNotCalled { sharedPreferences.edit() }
        confirmVerified(syncManager, sharedPreferences)
    }
}