package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.OrangTerpercayaDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya

class OrangTerpercayaRepository(private val orangTerpercayaDao: OrangTerpercayaDao) {
    val allOrangTerpercaya: LiveData<List<OrangTerpercaya>> = orangTerpercayaDao.getAll()

    fun add(orangTerpercaya: OrangTerpercaya) {
        orangTerpercayaDao.addOrangTerpercaya(orangTerpercaya)
    }
}