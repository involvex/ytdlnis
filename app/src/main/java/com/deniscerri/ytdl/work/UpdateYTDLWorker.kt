package com.involvex.ytmp3dlp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.involvex.ytmp3dlp.util.UpdateUtil


class UpdateYTDLWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        UpdateUtil(context).updateYTDL()
        return Result.success()
    }

}

