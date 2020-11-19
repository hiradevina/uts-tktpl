package id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.Emergency
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.entity.OrangTerpercaya
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.EmergencyRepository
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.repository.OrangTerpercayaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AddEmergencyViewModel(application: Application) : AndroidViewModel(application) {
    var hour = MutableLiveData<String>("00")
    var minute = MutableLiveData<String>("00")
    var second = MutableLiveData<String>("00")
    var trustedPeople = MutableLiveData<OrangTerpercaya>()
    var fotoUri = MutableLiveData<String>()
    var latitude = MutableLiveData<Double>()
    var longitude = MutableLiveData<Double>()

    private val repository: EmergencyRepository

    private fun formatTime(time: String): String {
        return String.format("%02d", time)
    }

     fun duration(): Int {
        var h = hour.value?.toInt()?.times(3600)
        var m = minute.value?.toInt()?.times(60)
        var s = second.value?.toInt()
        return h!! + m!! + s!!
    }

    init {
        val emergencyDao = AppDatabase.getDatabase(application).emergencyDao()
        repository =
            EmergencyRepository(
                emergencyDao
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert() = viewModelScope.launch(Dispatchers.IO) {

            repository.insert(
                Emergency(
                    0,
                    Date.from((LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .toInstant())),
                    latitude.value,
                    longitude.value,
                    null,
                    null,
                    null,
                    duration(),
                    trustedPeople.value?.nama,
                    true
                )
            )


    }

    fun notificationDescriptionText(): String {
        return "Alarm Lifecheker Anda telah berakhir, buka aplikasi kembali untuk matikan"
    }
}