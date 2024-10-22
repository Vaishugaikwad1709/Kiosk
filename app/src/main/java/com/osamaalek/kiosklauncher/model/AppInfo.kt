package com.osamaalek.kiosklauncher.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val label: CharSequence,      // CharSequence to store app name
    val packageName: String,      // String to store package name
    val icon: Drawable            // Drawable to store the app icon
)
