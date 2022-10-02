package com.android.favoritemakes.data.source.local.db.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "makes")
data class MakeModel(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "logo_url") val logoUrl: String,
    @ColumnInfo(name = "country_flag") val countryFlag: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
)