package com.involvex.ytmp3dlp.ui.more.settings.processing

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.involvex.ytmp3dlp.R
import com.involvex.ytmp3dlp.ui.more.settings.BaseSettingsFragment
import com.involvex.ytmp3dlp.ui.more.settings.SettingsRegistry
import com.involvex.ytmp3dlp.util.UiUtil

class ProcessingSettingsFragment : BaseSettingsFragment() {
    override val title: Int = R.string.processing
    private lateinit var sharedPrefs: SharedPreferences

    @SuppressLint("RestrictedApi")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceXMLRes = R.xml.processing_preferences
        setPreferencesFromResource(preferenceXMLRes, rootKey)
        SettingsRegistry.bindFragment(this, preferenceXMLRes)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Let the PreferenceFragmentCompat initialize the preferences hierarchy first
        super.onCreateView(inflater, container, savedInstanceState)

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(
                    colorScheme = darkColorScheme(
                        primary = Color(0xFF6366F1), // Premium Indigo
                        background = Color(0xFF09090B), // Sleek Zinc background
                        surface = Color(0xFF18181B)
                    )
                ) {
                    ProcessingSettingsContent()
                }
            }
        }
    }

    @Composable
    fun ProcessingSettingsContent() {
        val context = LocalContext.current
        val prefs = remember { PreferenceManager.getDefaultSharedPreferences(context) }
        
        // Settings State
        var useSponsorblock by remember { mutableStateOf(prefs.getBoolean("use_sponsorblock", true)) }
        var mtime by remember { mutableStateOf(prefs.getBoolean("mtime", false)) }
        var writeDescription by remember { mutableStateOf(prefs.getBoolean("write_description", false)) }
        var useExtraCommands by remember { mutableStateOf(prefs.getBoolean("use_extra_commands", true)) }
        
        var embedMetadata by remember { mutableStateOf(prefs.getBoolean("embed_metadata", true)) }
        var embedThumbnail by remember { mutableStateOf(prefs.getBoolean("embed_thumbnail", true)) }
        var cropThumbnail by remember { mutableStateOf(prefs.getBoolean("crop_thumbnail", true)) }

        var embedSubtitles by remember { mutableStateOf(prefs.getBoolean("embed_subtitles", true)) }

        var recodeVideo by remember { mutableStateOf(prefs.getBoolean("recode_video", false)) }
        var compatibleVideo by remember { mutableStateOf(prefs.getBoolean("compatible_video", false)) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    SettingsGroupHeader(title = stringResource(R.string.general))
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SwitchSettingItem(
                            title = stringResource(R.string.use_sponsorblock),
                            checked = useSponsorblock,
                            iconRes = R.drawable.ic_money,
                            onCheckedChange = {
                                useSponsorblock = it
                                prefs.edit { putBoolean("use_sponsorblock", it) }
                                findPref("use_sponsorblock")?.callChangeListener(it)
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.enable_mtime),
                            summary = stringResource(R.string.enable_mtime_summary),
                            checked = mtime,
                            iconRes = R.drawable.ic_clock,
                            onCheckedChange = {
                                mtime = it
                                prefs.edit { putBoolean("mtime", it) }
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.write_description),
                            summary = stringResource(R.string.write_description_summary),
                            checked = writeDescription,
                            iconRes = R.drawable.baseline_description_24,
                            onCheckedChange = {
                                writeDescription = it
                                prefs.edit { putBoolean("write_description", it) }
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.use_extra_commands),
                            summary = stringResource(R.string.use_extra_commands_summary),
                            checked = useExtraCommands,
                            iconRes = R.drawable.ic_terminal,
                            onCheckedChange = {
                                useExtraCommands = it
                                prefs.edit { putBoolean("use_extra_commands", it) }
                            }
                        )
                    }
                }

                item {
                    SettingsGroupHeader(title = stringResource(R.string.audio))
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SwitchSettingItem(
                            title = stringResource(R.string.embed_metadata),
                            summary = stringResource(R.string.embed_metadata_summary),
                            checked = embedMetadata,
                            iconRes = R.drawable.baseline_video_metadata,
                            onCheckedChange = {
                                embedMetadata = it
                                prefs.edit { putBoolean("embed_metadata", it) }
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.embed_thumb),
                            summary = stringResource(R.string.embed_thumb_summary),
                            checked = embedThumbnail,
                            iconRes = R.drawable.ic_image,
                            onCheckedChange = {
                                embedThumbnail = it
                                prefs.edit { putBoolean("embed_thumbnail", it) }
                                findPref("embed_thumbnail")?.callChangeListener(it)
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.crop_thumb),
                            summary = stringResource(R.string.crop_thumb_summary),
                            checked = cropThumbnail,
                            enabled = embedThumbnail,
                            iconRes = R.drawable.ic_cut,
                            onCheckedChange = {
                                cropThumbnail = it
                                prefs.edit { putBoolean("crop_thumbnail", it) }
                            }
                        )
                    }
                }

                item {
                    SettingsGroupHeader(title = stringResource(R.string.video))
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SwitchSettingItem(
                            title = stringResource(R.string.embed_subtitles),
                            summary = stringResource(R.string.embed_subs_summary),
                            checked = embedSubtitles,
                            iconRes = R.drawable.ic_subtitles,
                            onCheckedChange = {
                                embedSubtitles = it
                                prefs.edit { putBoolean("embed_subtitles", it) }
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.recode_video),
                            summary = stringResource(R.string.recode_video_summary),
                            checked = recodeVideo,
                            iconRes = R.drawable.baseline_video_metadata,
                            onCheckedChange = {
                                recodeVideo = it
                                prefs.edit { putBoolean("recode_video", it) }
                                findPref("recode_video")?.callChangeListener(it)
                            }
                        )
                        SwitchSettingItem(
                            title = stringResource(R.string.video_compatible),
                            summary = stringResource(R.string.video_compatible_summary),
                            checked = compatibleVideo,
                            iconRes = R.drawable.baseline_video_metadata,
                            onCheckedChange = {
                                compatibleVideo = it
                                prefs.edit { putBoolean("compatible_video", it) }
                                findPref("compatible_video")?.callChangeListener(it)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            UiUtil.showGenericConfirmDialog(context, context.getString(R.string.reset), context.getString(R.string.reset_preferences_in_screen)) {
                                resetPreferences(prefs.edit(), R.xml.processing_preferences)
                                requireActivity().recreate()
                                val fragmentId = findNavController().currentDestination?.id
                                findNavController().popBackStack(fragmentId!!, true)
                                findNavController().navigate(fragmentId)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Text(text = stringResource(R.string.reset), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    @Composable
    fun SettingsGroupHeader(title: String) {
        ProcessingSettingsGroupHeader(title)
    }

    @Composable
    fun SwitchSettingItem(
        title: String,
        summary: String? = null,
        checked: Boolean,
        enabled: Boolean = true,
        iconRes: Int? = null,
        onCheckedChange: (Boolean) -> Unit
    ) {
        ProcessingSwitchSettingItem(title, summary, checked, enabled, iconRes, onCheckedChange)
    }
}

@Composable
fun ProcessingSettingsGroupHeader(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
    )
}

@Composable
fun ProcessingSwitchSettingItem(
    title: String,
    summary: String? = null,
    checked: Boolean,
    enabled: Boolean = true,
    iconRes: Int? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = if (enabled) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) Color.White else Color.Gray
                )
                if (summary != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = summary,
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        lineHeight = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = checked,
                enabled = enabled,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSwitchSettingItem() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6366F1),
            background = Color(0xFF09090B),
            surface = Color(0xFF18181B)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProcessingSwitchSettingItem(
                title = "Use SponsorBlock",
                summary = "Remove sponsored segments automatically",
                checked = true,
                onCheckedChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsGroupHeader() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6366F1),
            background = Color(0xFF09090B),
            surface = Color(0xFF18181B)
        )
    ) {
        ProcessingSettingsGroupHeader(title = "General")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisabledSwitchItem() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6366F1),
            background = Color(0xFF09090B),
            surface = Color(0xFF18181B)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProcessingSwitchSettingItem(
                title = "Crop Thumbnail",
                summary = "Crop thumbnail to square",
                checked = false,
                enabled = false,
                onCheckedChange = {}
            )
        }
    }
}
