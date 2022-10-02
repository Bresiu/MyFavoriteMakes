package com.android.favoritemakes.data.mappers

import com.android.favoritemakes.data.source.local.db.room.model.MakeModel
import com.android.favoritemakes.data.source.remote.model.MakeJson
import com.android.favoritemakes.makeslist.MakeData

fun List<MakeJson>.mapToDBModels() = map {
    MakeModel(
        id = it.id,
        name = it.name,
        logoUrl = it.logoUrl,
        countryFlag = it.countryFlag,
        isFavorite = false,
    )
}

fun List<MakeModel>.mapToUIModels() = map {
    MakeData(
        id = it.id,
        name = it.name,
        logoUrl = it.logoUrl,
        countryFlag = it.countryFlag,
        isFavorite = it.isFavorite,
    )
}