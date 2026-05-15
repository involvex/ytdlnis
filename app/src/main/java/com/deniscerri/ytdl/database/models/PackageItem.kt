package com.involvex.ytmp3dlp.database.models

import com.involvex.ytmp3dlp.core.packages.PackageBase

data class PackageItem(
    val title: String,
    val plugin: PackageBase
) {
    fun getInstance(): PackageBase = plugin.getInstance()
}

