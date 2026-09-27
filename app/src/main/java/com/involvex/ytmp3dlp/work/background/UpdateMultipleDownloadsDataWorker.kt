package com.involvex.ytmp3dlp.work.background

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.involvex.ytmp3dlp.App
import com.involvex.ytmp3dlp.database.DBManager
import com.involvex.ytmp3dlp.database.repository.ResultRepository
import com.involvex.ytmp3dlp.util.NotificationUtil
import com.involvex.ytmp3dlp.work.YTDLPCoroutineWorker
import com.involvex.ytmp3dlp.work.setForegroundSafely
import kotlinx.coroutines.runBlocking

class UpdateMultipleDownloadsDataWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : YTDLPCoroutineWorker(context, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val workNotif = NotificationUtil(App.Companion.instance).createDataUpdateNotification()

        return ForegroundInfo(
            2000000000,
            workNotif,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }


    override suspend fun runWork(): Result {
        val dbManager = DBManager.Companion.getInstance(context)
        val dao = dbManager.downloadDao
        val resDao = dbManager.resultDao
        val commandTemplateDao = dbManager.commandTemplateDao
        val resultRepo = ResultRepository(resDao, commandTemplateDao, context)
        val ids = inputData.getLongArray("ids")!!.toMutableList()

        setForegroundSafely()
        try{
            ids.forEach {
                if (!isStopped){
                    val d = dao.getDownloadById(it)
                    if (d.title.isNotBlank() && d.author.isNotBlank() && d.thumb.isNotBlank()) {
                        return@forEach
                    }

                    runCatching {
                        runBlocking {
                            resultRepo.updateDownloadItem(d)?.apply {
                                val dd = dao.getNullableDownloadById(it)
                                if (dd != null) {
                                    d.status = dd.status
                                    dao.updateWithoutUpsert(this)
                                }
                            }
                        }
                    }
                }else{
                    throw Exception()
                }
            }


        }catch (e: Exception){
            ids.clear()
            return Result.failure()
        }

        return Result.success()
    }

}