package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.EmergencyDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.OrangTerpercayaDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class EmergencyRepository(private val emergencyDao: EmergencyDao) {
    val emergencies: LiveData<List<Emergency>> = emergencyDao.getAll()

    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    suspend fun getActiveLifecheck(): Emergency? {
        return emergencyDao.getActiveLifecheck().firstOrNull()
    }

    suspend fun isLifecheckRunning(): Boolean {
        return getActiveLifecheck() != null
    }

    fun insert(emergency: Emergency) {
       InsertEmergencyAsyncTask(emergencyDao).execute(emergency)
    }

    suspend fun get(id: Int): Emergency {
        return emergencyDao.get(id)
    }

    fun update(emergency: Emergency) {
        UpdateEmergencyAsyncTask(emergencyDao).execute(emergency)
    }

    private class InsertEmergencyAsyncTask(emergencyDao: EmergencyDao) :
        AsyncTask<Emergency?, Void?, Void?>() {
        private val emergencyDao: EmergencyDao

        init {
            this.emergencyDao = emergencyDao
        }

        override fun doInBackground(vararg emergency: Emergency?): Void? {
            emergency[0]?.let { emergencyDao.insert(it) }
            return null
        }
    }

    private class UpdateEmergencyAsyncTask(emergencyDao: EmergencyDao) :
        AsyncTask<Emergency?, Void?, Void?>() {
        private val emergencyDao: EmergencyDao

        init {
            this.emergencyDao = emergencyDao
        }

        override fun doInBackground(vararg emergency: Emergency?): Void? {
            Log.d("UpdateEMergency", "updating")
            emergency[0]?.let { emergencyDao.update(it) }
            return null
        }
    }
}