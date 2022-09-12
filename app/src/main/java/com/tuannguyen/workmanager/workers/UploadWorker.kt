package com.tuannguyen.workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.tuannguyen.workmanager.Utils.Constants

private const val TAG = "UploadWorker"
class UploadWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    var uri: String? = ""

    override fun doWork(): Result {
        uri = inputData.getString(Constants.PHOTO_URI)
        val success = upload(uri)
        return if (success) {
            val output = workDataOf(Constants.RESULT to uploadResult)
            Result.success(output)
        } else {
            Result.failure()
        }
    }

    private fun upload(uri: String?): Boolean {
        return try {
            // TODO: upload photo
            true
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            false
        }
    }
}