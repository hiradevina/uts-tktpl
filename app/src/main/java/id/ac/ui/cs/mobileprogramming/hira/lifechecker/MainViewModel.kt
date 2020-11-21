package id.ac.ui.cs.mobileprogramming.hira.lifechecker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.ProfileRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProfileRepository
    private val emergencyRepository: EmergencyRepository
    init {
        val profileDao = AppDatabase.getDatabase(application).profileDao()
        repository =
            ProfileRepository(
                profileDao
            )
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        emergencyRepository = EmergencyRepository(emergencyDao)
    }

    fun isProfileExist(): Boolean {
        return repository.isProfileExist()
    }

    fun isLifecheckRunning(): Boolean {
        return emergencyRepository.isLifecheckRunning()
    }
}