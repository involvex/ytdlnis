package com.involvex.ytmp3dlp.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.preference.PreferenceManager
import com.involvex.ytmp3dlp.R
import com.involvex.ytmp3dlp.database.DBManager
import com.involvex.ytmp3dlp.database.dao.DownloadDao
import com.involvex.ytmp3dlp.database.enums.DownloadType
import com.involvex.ytmp3dlp.database.models.DownloadItem
import com.involvex.ytmp3dlp.database.models.DownloadItemConfigureMultiple
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File

class DownloadMultipleCardViewModel(application: Application) : AndroidViewModel(application) {
    private val dbManager: DBManager = DBManager.getInstance(application)
    private val dao: DownloadDao = dbManager.downloadDao
    private val repository: DownloadRepository = DownloadRepository(dao)

    val totalProcessingFileSize: Flow<Long> = repository.getProcessingSizeMetadata()
        .map { items ->
            items.sumOf { item ->
                if (item.type == DownloadType.video) {
                    if (item.format.filesize <= 5L) 0L
                    else {
                        val audioSize = if (item.videoPreferences.removeAudio) 0L
                        else {
                            item.allFormats
                                .filter { f -> item.videoPreferences.audioFormatIDs.contains(f.format_id) }
                                .sumOf { f -> f.filesize }
                        }
                        item.format.filesize + audioSize
                    }
                } else if (item.type == DownloadType.audio) {
                    item.format.filesize
                } else 0L
            }
        }
        .flowOn(Dispatchers.Default)

    val processingDownloads: Flow<PagingData<DownloadItemConfigureMultiple>> = Pager(
        config = PagingConfig(
            pageSize = 40,
            enablePlaceholders = false,
            initialLoadSize = 80
        ),
        pagingSourceFactory = { dao.getProcessingDownloads() }
    ).flow.cachedIn(viewModelScope)

    val processingIds: Flow<List<Long>> = dao.getProcessingDownloadsIds()

    private val _mainProcessingItemId = MutableStateFlow<Long?>(null)
    fun setMainProcessingItem(id: Long?) {
        _mainProcessingItemId.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val firstProcessingDownload: Flow<DownloadItem?> = _mainProcessingItemId.flatMapLatest { id ->
        if (id != null) {
            dao.getDownloadByIdFlow(id)
        } else {
            dao.getFirstProcessingDownloadFlow()
        }
    }.flowOn(Dispatchers.IO)

}

