package com.android.favoritemakes.makeslist

import app.cash.turbine.test
import com.android.favoritemakes.coCalledOnce
import com.android.favoritemakes.data.provideTestMakeModelList
import com.android.favoritemakes.data.source.local.db.MakeRepository
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
        val makeModels = provideTestMakeModelList()
        val makeRepository: MakeRepository = mockk {
            every { getAll() } returns flowOf(makeModels)
        }
        val makesListViewModel =
            MakesListViewModel(makeRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.fetchMakes().test {
            val uiModels = awaitItem()
            assertThat(uiModels).hasSize(makeModels.size)
            awaitComplete()
        }
        coCalledOnce { makeRepository.getAll() }
        confirmVerified(makeRepository)
    }

    @Test
    fun `test unfavorite make`() = runTest {
        val makeId = 0L
        val currentFavoriteState = true
        val makeRepository: MakeRepository = mockk {
            coEvery { unfavoriteMake(makeId) } just Runs
        }
        val makesListViewModel =
            MakesListViewModel(makeRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.toggleFavoriteMake(makeId, currentFavoriteState)
        coCalledOnce { makeRepository.unfavoriteMake(makeId) }
        confirmVerified(makeRepository)
    }

    @Test
    fun `test favorite make`() = runTest {
        val makeId = 0L
        val currentFavoriteState = false
        val makeRepository: MakeRepository = mockk {
            coEvery { favoriteMake(makeId) } just Runs
        }
        val makesListViewModel =
            MakesListViewModel(makeRepository, mockk(), UnconfinedTestDispatcher(testScheduler))
        makesListViewModel.toggleFavoriteMake(makeId, currentFavoriteState)
        coCalledOnce { makeRepository.favoriteMake(makeId) }
        confirmVerified(makeRepository)
    }
}