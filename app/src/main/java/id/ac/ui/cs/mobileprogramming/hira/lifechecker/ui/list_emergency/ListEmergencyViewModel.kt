package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_emergency

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository

class ListEmergencyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmergencyRepository
    val emergencies: LiveData<List<Emergency>>

    init {
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        repository =
            EmergencyRepository(
                emergencyDao
            )
        emergencies = repository.emergencies
    }
}