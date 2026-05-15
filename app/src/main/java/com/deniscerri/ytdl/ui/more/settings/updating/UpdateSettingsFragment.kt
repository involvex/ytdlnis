package com.involvex.ytmp3dlp.ui.more.settings.updating

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.involvex.ytmp3dlp.BuildConfig
import com.involvex.ytmp3dlp.R
import com.involvex.ytmp3dlp.database.viewmodel.SettingsViewModel
import com.involvex.ytmp3dlp.database.viewmodel.YTDLPViewModel
import com.involvex.ytmp3dlp.ui.more.settings.BaseSettingsFragment
import com.involvex.ytmp3dlp.ui.more.settings.SettingsRegistry
import com.involvex.ytmp3dlp.util.FileUtil
import com.involvex.ytmp3dlp.util.UiUtil
import com.involvex.ytmp3dlp.util.UpdateUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class UpdateSettingsFragment : BaseSettingsFragment() {
    override val title: Int = R.string.updating
    private lateinit var preferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceXMLRes = R.xml.updating_preferences
        setPreferencesFromResource(preferenceXMLRes, rootKey)
        SettingsRegistry.bindFragment(this, preferenceXMLRes)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        findPreference<Preference>("reset_preferences")?.setOnPreferenceClickListener {
            UiUtil.showGenericConfirmDialog(requireContext(), getString(R.string.reset), getString(R.string.reset_preferences_in_screen)) {
                resetPreferences(preferences.edit(), preferenceXMLRes)
                requireActivity().recreate()
                val fragmentId = findNavController().currentDestination?.id
                findNavController().popBackStack(fragmentId!!,true)
                findNavController().navigate(fragmentId)
            }
            true
        }
    }
}

