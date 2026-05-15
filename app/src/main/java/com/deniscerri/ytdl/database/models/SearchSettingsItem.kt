package com.involvex.ytmp3dlp.database.models

import androidx.preference.Preference
import com.involvex.ytmp3dlp.ui.more.settings.SettingModule

data class SearchSettingsItem(
    val preference: Preference,
    val xmlId: Int,
    val module: SettingModule?,
    val groupTitle: String? = null,
    val isHeader: Boolean = false,
    var canRebind: Boolean = true
)

