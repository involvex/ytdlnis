package com.involvex.ytmp3dlp.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.involvex.ytmp3dlp.R
import com.involvex.ytmp3dlp.database.DBManager
import com.involvex.ytmp3dlp.database.enums.DownloadType
import com.involvex.ytmp3dlp.database.models.DownloadItem
import com.involvex.ytmp3dlp.database.models.Format
import com.involvex.ytmp3dlp.database.models.FormatRecyclerView
import com.involvex.ytmp3dlp.database.models.ResultItem
import com.involvex.ytmp3dlp.database.repository.DownloadRepository
import com.involvex.ytmp3dlp.ui.downloadcard.FormatSelectionBottomSheetDialog.FormatCategory
import com.involvex.ytmp3dlp.ui.downloadcard.FormatSelectionBottomSheetDialog.FormatSorting
import com.involvex.ytmp3dlp.ui.downloadcard.FormatTuple
import com.involvex.ytmp3dlp.ui.downloadcard.MultipleItemFormatTuple
import com.involvex.ytmp3dlp.util.Extensions.isYoutubeURL
import com.involvex.ytmp3dlp.util.FileUtil
import com.involvex.ytmp3dlp.util.FormatUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.io.File

class DownloadCardViewModel(application: Application) : AndroidViewModel(application) {
    var resultItem: ResultItem? = null
        private set

    var downloadItem: DownloadItem? = null
        private set

    fun setDownloadItem(item: DownloadItem?) {
        downloadItem = item
    }

    fun setResultItem(item: ResultItem?) {
        resultItem = item
    }
}

