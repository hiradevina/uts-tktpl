package id.ac.ui.cs.mobileprogramming.hira.lifechecker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.ProfileRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProfileRepository
    init {
        val profileDao = AppDatabase.getDatabase(application).profileDao()
        repository =
            ProfileRepository(
                profileDao
            )
    }

    fun isProfileExist(): Boolean {
        return repository.isProfileExist()
    }
}