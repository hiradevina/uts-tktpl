package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_profile

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Profile
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.ProfileRepository

class AddProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileRepository
    val profiles: LiveData<List<Profile>>

    init {
        val profileDao = AppDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDao)
        profiles = repository.profiles
    }

    // data
    var nama = MutableLiveData<String>()
    var alamat = MutableLiveData<String>()
    var golonganDarah = MutableLiveData<String>()
    var notelp = MutableLiveData<String>()

    fun insert() {
        if (isValid()) {
            repository.add(
                Profile(
                    nama.value!!,
                    alamat.value!!,
                    golonganDarah.value!!,
                    notelp.value!!
                )
            )
        }
    }

    fun isValid(): Boolean {
        return !TextUtils.isEmpty(nama.value) && !TextUtils.isEmpty(alamat.value) && !TextUtils.isEmpty(
            golonganDarah.value
        ) && !TextUtils.isEmpty(notelp.value)
    }

}