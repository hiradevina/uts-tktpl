package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.ProfileDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Profile

class ProfileRepository(private val profileDao: ProfileDao) {

    val profiles: LiveData<List<Profile>> = profileDao.get()

    fun add(profile: Profile) {
        profileDao.register(profile)
    }
}