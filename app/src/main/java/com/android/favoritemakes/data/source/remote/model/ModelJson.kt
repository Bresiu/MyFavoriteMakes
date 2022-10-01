package com.android.favoritemakes.data.source.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MakeJson(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "logo_url") val logoUrl: String,
    @Json(name = "country_flag") val countryFlag: String,
)