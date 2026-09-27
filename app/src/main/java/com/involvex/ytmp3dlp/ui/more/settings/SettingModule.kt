package com.involvex.ytmp3dlp.ui.more.settings

import androidx.preference.Preference

interface SettingModule {
    fun bindLogic(pref: Preference, host: SettingHost)
}
