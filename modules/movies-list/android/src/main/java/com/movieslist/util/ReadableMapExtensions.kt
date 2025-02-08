package com.movieslist.util

import com.facebook.react.bridge.ReadableMap

fun ReadableMap.getBooleanOrDefault(key: String, defaultValue: Boolean = false): Boolean {
    return if (hasKey(key) && !isNull(key)) {
        try {
            getBoolean(key)
        } catch (e: Exception) {
            defaultValue
        }
    } else {
        defaultValue
    }
}