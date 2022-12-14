package com.android.favoritemakes.data

import com.android.favoritemakes.data.mappers.mapToDBModels
import com.android.favoritemakes.data.source.remote.model.MakeJson

fun provideTestMakesJsonList() = listOf(
    MakeJson(0, "Tesla", "tesla_url", "πΊπΈ"),
    MakeJson(1, "Audi", "audi_url", "π©πͺ")
)

fun provideTestMakesModelList() = provideTestMakesJsonList().mapToDBModels()