package com.involvex.ytmp3dlp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.involvex.ytmp3dlp.core.RuntimeManager
import com.involvex.ytmp3dlp.database.DBManager
import com.involvex.ytmp3dlp.database.repository.DownloadRepository
import com.involvex.ytmp3dlp.util.NotificationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PauseDownloadNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(c: Context, intent: Intent) {
        val result = goAsync()
        val id = intent.getIntExtra("itemID", 0)
        if (id != 0) {
            runCatching {
                val title = intent.getStringExtra("title")
                val notificationUtil = NotificationUtil(c)
                notificationUtil.cancelDownloadNotification(id)
                RuntimeManager.getInstance().destroyProcessById(id.toString())
                val dbManager = DBManager.getInstance(c)
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        val item = dbManager.downloadDao.getDownloadById(id.toLong())
                        item.status = DownloadRepository.Status.Paused.toString()
                        dbManager.downloadDao.update(item)
                    }finally {
                        withContext(Dispatchers.Main){
                            notificationUtil.createResumeDownload(id, title)
                            result.finish()
                        }
                    }
                }
            }
        }
    }
}

