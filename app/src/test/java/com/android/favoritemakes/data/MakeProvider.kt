package com.android.favoritemakes.data

import com.android.favoritemakes.data.mappers.mapToDBModels
import com.android.favoritemakes.data.source.remote.model.MakeJson

fun provideTestMakeJsonList() = listOf(
    MakeJson(0, "Tesla", "tesla_url", "🇺🇸"),
    MakeJson(1, "Audi", "audi_url", "🇩🇪")
)

fun provideTestMakeModelList() = provideTestMakeJsonList().mapToDBModels()