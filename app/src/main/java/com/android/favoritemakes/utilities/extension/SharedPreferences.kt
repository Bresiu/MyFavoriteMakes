package com.android.favoritemakes.utilities.extension

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class SharedPreferencesBooleanDelegate(private val prefs: SharedPreferences, private val defaultValue: Boolean = false) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
        prefs.getBoolean(property.name, defaultValue)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        prefs.edit {
            putBoolean(property.name, value)
        }
    }
}