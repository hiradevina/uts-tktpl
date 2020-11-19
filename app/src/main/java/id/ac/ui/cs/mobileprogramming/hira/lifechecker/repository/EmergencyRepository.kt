package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.EmergencyDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency

class EmergencyRepository(private val emergencyDao: EmergencyDao) {
    val emergencies: LiveData<List<Emergency>> = emergencyDao.getAll()

    fun getActiveLifecheck() : Emergency? {
        return emergencyDao.getActiveLifecheck().value?.first()
    }

    fun isLifecheckRunning(): Boolean {
        return getActiveLifecheck() != null
    }

    fun insert(emergency: Emergency) {
        return emergencyDao.insert(emergency)
    }

    fun get(id: Int): Emergency {
        return emergencyDao.get(id)
    }

    fun update(emergency: Emergency) {
        return emergencyDao.update(emergency)
    }
}