package com.involvex.ytmp3dlp.ui.more.settings.advanced

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.involvex.ytmp3dlp.R
import com.involvex.ytmp3dlp.ui.adapter.SortableTextItemAdapter
import com.involvex.ytmp3dlp.ui.more.settings.BaseSettingsFragment
import com.involvex.ytmp3dlp.ui.more.settings.SettingsRegistry
import com.involvex.ytmp3dlp.util.UiUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AdvancedSettingsFragment : BaseSettingsFragment() {
    override val title: Int = R.string.advanced
    @SuppressLint("RestrictedApi")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceXMLRes = R.xml.advanced_preferences
        setPreferencesFromResource(preferenceXMLRes, rootKey)
        SettingsRegistry.bindFragment(this, preferenceXMLRes)

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val editor = prefs.edit()

        findPreference<Preference>("reset_preferences")?.setOnPreferenceClickListener {
            UiUtil.showGenericConfirmDialog(requireContext(), getString(R.string.reset), getString(R.string.reset_preferences_in_screen)) {
                resetPreferences(editor, preferenceXMLRes)
                requireActivity().recreate()
                val fragmentId = findNavController().currentDestination?.id
                findNavController().popBackStack(fragmentId!!,true)
                findNavController().navigate(fragmentId)
            }
            true
        }

    }
}

