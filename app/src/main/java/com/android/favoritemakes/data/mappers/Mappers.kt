package com.android.favoritemakes.data.mappers

import com.android.favoritemakes.data.source.local.db.room.model.Make
import com.android.favoritemakes.data.source.remote.model.MakeJson

fun List<MakeJson>.mapToModels() = map {
    Make(
        id = it.id,
        name = it.name,
        logoUrl = it.logoUrl,
        countryFlag = it.countryFlag,
        isFavorite = false,
    )
}