package com.android.favoritemakes

import android.util.Log
import io.mockk.*

internal fun calledOnce(verifyBlock: MockKVerificationScope.() -> Unit) =
    verify(exactly = 1, verifyBlock = verifyBlock)

internal fun coCalledOnce(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(exactly = 1, verifyBlock = verifyBlock)

internal fun wasNotCalled(verifyBlock: MockKVerificationScope.() -> Unit) =
    verify(exactly = 0, verifyBlock = verifyBlock)

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