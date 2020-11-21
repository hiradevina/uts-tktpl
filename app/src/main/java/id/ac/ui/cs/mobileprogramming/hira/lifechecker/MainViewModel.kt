package id.ac.ui.cs.mobileprogramming.hira.lifechecker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.Coroutines
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.ProfileRepository
import kotlinx.coroutines.Job

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileRepository
    private val emergencyRepository: EmergencyRepository

    val emergency = MutableLiveData<Emergency>()
    private lateinit var job: Job

    init {
        val profileDao = AppDatabase.getDatabase(application).profileDao()
        repository =
            ProfileRepository(
                profileDao
            )

        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        emergencyRepository = EmergencyRepository(emergencyDao)
        job = Coroutines.ioThenMain(
            { emergencyRepository.getActiveLifecheck() },
            {
                emergency.value = it
            }
        )
    }

    fun isProfileExist(): Boolean {
        return repository.isProfileExist()
    }

    fun isLifecheckRunning(): Boolean {
        return emergency.value != null
    }
}