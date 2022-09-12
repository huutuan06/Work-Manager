package com.tuannguyen.workmanager

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.work.*
import com.tuannguyen.workmanager.Utils.Constants
import com.tuannguyen.workmanager.workers.UploadWorker

class MainViewModel(
    private val application: Application
): ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    val workInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("")

    val constrains = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val photoData = workDataOf(Constants.PHOTO_URI to uri)

    val uploadPhotoRequest = OneTimeWorkRequestBuilder<UploadWorker>()
        .setConstraints(constrains)
        .setInputData(photoData)
        .build()

    fun uploadPhoto() {
        WorkManager.getInstance(application).enqueue(uploadPhotoRequest)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return MainViewModel(
                    application
                ) as T
            }
        }
    }
}