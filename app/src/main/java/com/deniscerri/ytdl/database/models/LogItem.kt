package com.involvex.ytmp3dlp.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.involvex.ytmp3dlp.database.enums.DownloadType

@Entity(tableName = "logs")
data class LogItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var content: String,
    var format: Format,
    var downloadType: DownloadType,
    var downloadTime: Long,
)


