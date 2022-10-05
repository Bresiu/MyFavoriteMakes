package com.android.favoritemakes.utils

import android.util.Log
import io.mockk.*

internal fun coCalledOnce(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(exactly = 1, verifyBlock = verifyBlock)

internal fun coWasNotCalled(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(inverse = true, verifyBlock = verifyBlock)

fun mockkLogger() {
    mockkStatic(Log::class)
    every { Log.v(any(), any()) } returns 0
    every { Log.d(any(), any()) } returns 0
    every { Log.i(any(), any()) } returns 0
    every { Log.e(any(), any()) } returns 0
    every { Log.e(any(), any(), any()) } returns 0
}