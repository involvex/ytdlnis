package com.involvex.ytmp3dlp.ui.downloadcard

import com.involvex.ytmp3dlp.database.models.ResultItem

interface GUISync {
    fun updateTitleAuthor(t: String, a: String)
    fun updateUI(res: ResultItem?)
}

