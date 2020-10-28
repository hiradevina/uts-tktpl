package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.list_orang_terpercaya

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.OrangTerpercayaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOrangTerpercayaViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository: OrangTerpercayaRepository
    val allOrangTerpercaya: LiveData<List<OrangTerpercaya>>

    init {
        val orangTerpercayaDao = AppDatabase.getDatabase(application).orangTerpercayaDao()
        repository =
            OrangTerpercayaRepository(
                orangTerpercayaDao
            )
        allOrangTerpercaya = repository.allOrangTerpercaya
    }

}
