package com.involvex.ytmp3dlp.database.models

import com.involvex.ytmp3dlp.database.enums.DownloadType

data class DownloadSizeMetadata(
    val id: Long,
    val type: DownloadType,
    val format: Format,
    val allFormats: List<Format>,
    val videoPreferences: VideoPreferences
)

