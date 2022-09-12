package com.tuannguyen.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.tuannguyen.workmanager.Utils.Constants
import com.tuannguyen.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.workInfo.observe(this, workInfosObserver)
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            val workInfo = listOfWorkInfo[0]
            if (workInfo.state.isFinished) {
                val uploadResult = workInfo.outputData.getString(Constants.RESULT)
                Toast.makeText(this, "Upload photo successfully!", Toast.LENGTH_LONG).show()
            } else {
                // In progress
            }
        }
    }
}