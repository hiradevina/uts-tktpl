package id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.dao.ProfileDao
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Profile
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ProfileRepository(private val profileDao: ProfileDao) {

    val profiles: LiveData<List<Profile>> = profileDao.get()

    fun add(profile: Profile) {
        InsertProfileAsyncTask(profileDao).execute(profile)
    }

    fun isProfileExist(): Boolean {
        return !profiles.value.isNullOrEmpty()
    }

    fun get(id: Int): Profile? {
        return profileDao.get().value?.first()
    }

    private class InsertProfileAsyncTask(profileDao: ProfileDao) : AsyncTask<Profile?, Void?, Void?>() {
        private  val profileDao: ProfileDao

        init {
            this.profileDao = profileDao
        }

        override fun doInBackground(vararg profiles: Profile?): Void? {
            profiles[0]?.let { profileDao.register(it) }
            return  null
        }
    }
}