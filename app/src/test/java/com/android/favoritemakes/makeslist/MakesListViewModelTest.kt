package com.android.favoritemakes.makeslist

import app.cash.turbine.test
import com.android.favoritemakes.utils.coCalledOnce
import com.android.favoritemakes.data.provideTestMakesModelList
import com.android.favoritemakes.data.source.local.db.MakesLocalRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MakesListViewModelTest {

    @Test
    fun `test fetchMakes`() = runTest {
        val makeModels = provideTestMakesModelList()
        val makesLocalRepository: MakesLocalRepository = mockk {
            every { getAll() } returns flowOf(makeModels)
        }
        val makesListViewModel =
            MakesListViewModel(makesLocalRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.fetchMakes().test {
            val uiModels = awaitItem()
            assertThat(uiModels).hasSize(makeModels.size)
            awaitComplete()
        }
        coCalledOnce { makesLocalRepository.getAll() }
        confirmVerified(makesLocalRepository)
    }

    @Test
    fun `test unfavorite make`() = runTest {
        val makeId = 0L
        val currentFavoriteState = true
        val makesLocalRepository: MakesLocalRepository = mockk {
            coEvery { unfavoriteMake(makeId) } just Runs
        }
        val makesListViewModel =
            MakesListViewModel(makesLocalRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.toggleFavoriteMake(makeId, currentFavoriteState)
        coCalledOnce { makesLocalRepository.unfavoriteMake(makeId) }
        confirmVerified(makesLocalRepository)
    }

    @Test
    fun `test favorite make`() = runTest {
        val makeId = 0L
        val currentFavoriteState = false
        val makesLocalRepository: MakesLocalRepository = mockk {
            coEvery { favoriteMake(makeId) } just Runs
        }
        val makesListViewModel =
            MakesListViewModel(makesLocalRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.toggleFavoriteMake(makeId, currentFavoriteState)
        coCalledOnce { makesLocalRepository.favoriteMake(makeId) }
        confirmVerified(makesLocalRepository)
    }
}