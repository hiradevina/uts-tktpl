package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_orang_terpercaya

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.OrangTerpercayaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddOrangTerpercayaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrangTerpercayaRepository
    val allOrangTerpercaya: LiveData<List<OrangTerpercaya>>

    // data
    var nama = MutableLiveData<String>()
    var relasi = MutableLiveData<String>()
    var notelp = MutableLiveData<String>()
    var foto = MutableLiveData<String>()

    init {
        val orangTerpercayaDao =
            AppDatabase.getDatabase(application).orangTerpercayaDao()
        repository =
            OrangTerpercayaRepository(
                orangTerpercayaDao
            )
        allOrangTerpercaya = repository.allOrangTerpercaya
    }

    fun insert() {
        if (isValid()) {
            Log.d("AddOranercayaViewModel", "insert")
            repository.add(
                OrangTerpercaya(
                    0,
                    nama.value!!,
                    notelp.value!!,
                    relasi.value!!,
                    foto.value!!
                )
            )
        }
    }


    fun isValid(): Boolean {
        Log.d("ViewModel", "nama ${nama.value}")
        Log.d("ViewModel", "nama ${relasi.value}")
        Log.d("ViewModel", "nama ${notelp.value}")
        Log.d("ViewModel", "nama ${foto.value}")
        return !TextUtils.isEmpty(nama.value) && !TextUtils.isEmpty(relasi.value) && !TextUtils.isEmpty(
            notelp.value
        ) && !TextUtils.isEmpty(foto.value)
    }

    fun showAddPhotoButton() : Int {
        return if(!TextUtils.isEmpty((foto.value))) {
            View.VISIBLE
        } else View.GONE
    }
}