package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.Coroutines
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.OrangTerpercayaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailOrangTerpercayaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OrangTerpercayaRepository
    val allOrangTerpercaya: LiveData<List<OrangTerpercaya>>

    var orangTerpercaya = MutableLiveData<OrangTerpercaya>()
    private lateinit var job: Job

    init {
        val orangTerpercayaDao = AppDatabase.getDatabase(application).orangTerpercayaDao()
        repository =
            OrangTerpercayaRepository(
                orangTerpercayaDao
            )
        allOrangTerpercaya = repository.allOrangTerpercaya
    }

    fun getSelectedOrangTerpercaya(index: Int) {
         job = Coroutines.ioThenMain(
             {repository.get(index)},
             {orangTerpercaya.value = it }
         )
    }
}