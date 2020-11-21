package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.OrangTerpercayaDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class OrangTerpercayaRepository(private val orangTerpercayaDao: OrangTerpercayaDao) {
    val allOrangTerpercaya: LiveData<List<OrangTerpercaya>> = orangTerpercayaDao.getAll()

    fun add(orangTerpercaya: OrangTerpercaya) {
        InsertOrangTerpercayaAsyncTask(orangTerpercayaDao).execute(orangTerpercaya)

    }

    suspend fun get(id: Int): OrangTerpercaya {
        return orangTerpercayaDao.getOrangTerpercaya(id)
    }

    private class InsertOrangTerpercayaAsyncTask(orangTerpercayaDao: OrangTerpercayaDao) :
        AsyncTask<OrangTerpercaya?, Void?, Void?>() {
        private val orangTerpercayaDao: OrangTerpercayaDao

        init {
            this.orangTerpercayaDao = orangTerpercayaDao
        }

        override fun doInBackground(vararg orangTerpercaya: OrangTerpercaya?): Void? {
            orangTerpercaya[0]?.let {
                Log.d("test", "$it")
                orangTerpercayaDao.addOrangTerpercaya(it) }
            Log.d("OrangTerpercayaAsyncTsk", "insert orang terpercaya")
            return null
        }
    }
}